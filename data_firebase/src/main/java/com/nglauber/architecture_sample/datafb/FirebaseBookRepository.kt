package com.nglauber.architecture_sample.datafb

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.nglauber.architecture_sample.core.ErrorEntity
import com.nglauber.architecture_sample.core.ResultState
import com.nglauber.architecture_sample.core.ResultState.Companion.flowRequest
import com.nglauber.architecture_sample.domain.entities.Book
import com.nglauber.architecture_sample.domain.repositories.BooksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*

class FirebaseBookRepository : BooksRepository {

    private val fbAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val storageRef = FirebaseStorage.getInstance().reference.child(BOOKS_KEY)

    init {
        fbAuth.addAuthStateListener {
            // Terminate Firestore and Storage after logout to avoid crash.
            if (it.currentUser == null) {
                firestore.terminate()
                storageRef.delete()
            }
        }
    }

    override fun saveBook(book: Book): Flow<ResultState<Unit>> {
        return callbackFlow {
            trySend(ResultState.Loading)
            val currentUser = fbAuth.currentUser
            if (currentUser == null) {
                trySend(ResultState.Error(ErrorEntity(RuntimeException("Unauthorized used."))))
            } else {
                val db = firestore
                val collection = db.collection(BOOKS_KEY)
                val saveTask = if (book.id.isBlank()) {
                    collection.add(book)
                        .continueWithTask { task ->
                            val doc = task.result
                            book.id = doc?.id ?: UUID.randomUUID().toString()
                            doc?.update(mapOf(USER_ID_KEY to currentUser.uid, ID_KEY to book.id))
                        }
                } else {
                    collection.document(book.id)
                        .set(book, SetOptions.merge())
                }
                saveTask
                    .continueWith { task ->
                        if (task.isSuccessful) {
                            if (book.coverUrl.startsWith("file://")) {
                                uploadFile(book)
                            } else {
                                Tasks.forResult(Unit)
                            }
                        } else {
                            Tasks.forException(
                                task.exception ?: RuntimeException("Fail to save book")
                            )
                        }
                    }
                    .addOnSuccessListener { trySend(ResultState.Success(Unit)) }
                    .addOnFailureListener { e -> trySend(ResultState.Error(ErrorEntity(e))) }

                awaitClose()
            }
        }
    }

    override fun loadBooks(): Flow<ResultState<List<Book>>> {
        return callbackFlow {
            trySend(ResultState.Loading)
            val currentUser = fbAuth.currentUser
            val subscription = firestore.collection(BOOKS_KEY)
                .whereEqualTo(USER_ID_KEY, currentUser?.uid)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        trySend(ResultState.Error(ErrorEntity(e)))
                        close(e)
                        return@addSnapshotListener
                    }
                    if (snapshot != null && !snapshot.isEmpty) {
                        val books = snapshot.map { document ->
                            document.toObject(Book::class.java)
                        }
                        trySend(ResultState.Success(books))
                    } else {
                        trySend(ResultState.Success(emptyList()))
                    }
                }

            awaitClose {
                subscription.remove()
            }
        }
    }

    override fun loadBook(bookId: String): Flow<ResultState<Book?>> {
        return callbackFlow {
            trySend(ResultState.Loading)
            val subscription = firestore.collection(BOOKS_KEY)
                .document(bookId)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        close(e)
                        return@addSnapshotListener
                    }
                    if (snapshot != null && snapshot.exists()) {
                        val book = snapshot.toObject(Book::class.java)
                        book?.let {
                            trySend(ResultState.Success(it))
                        }
                    } else {
                        trySend(ResultState.Success(null))
                    }
                }
            awaitClose { subscription.remove() }
        }
    }

    override fun remove(book: Book): Flow<ResultState<Unit>> {
        return flowRequest(
            dispatcher = Dispatchers.IO
        ) {
            val db = firestore
            val deleteBookTask = db.collection(BOOKS_KEY)
                .document(book.id)
                .delete()

            Tasks.await(deleteBookTask)
            if (deleteBookTask.isSuccessful) {
                if (book.coverUrl.isNotBlank()) {
                    val deleteImageTask = storageRef.child(book.id)
                        .delete()
                    Tasks.await(deleteImageTask)
                    if (deleteImageTask.isSuccessful) {
                        ResultState.Success(Unit)
                    } else {
                        throw Exception(deleteImageTask.exception)
                    }
                } else {
                    ResultState.Success(Unit)
                }
            } else {
                throw Exception(deleteBookTask.exception)
            }
        }
    }

    private fun uploadFile(book: Book): Task<Void> {
        return uploadPhoto(book).continueWithTask { urlTask ->
            File(book.coverUrl).delete()
            book.coverUrl = urlTask.result.toString()
            firestore.collection(BOOKS_KEY)
                .document(book.id)
                .update(COVER_URL_KEY, book.coverUrl)
        }
    }

    private fun uploadPhoto(book: Book): Task<Uri> {
        compressPhoto(book.coverUrl)
        val storageRef = storageRef.child(book.id)
        return storageRef.putFile(Uri.parse(book.coverUrl))
            .continueWithTask { uploadTask ->
                uploadTask.result?.storage?.downloadUrl
            }
    }

    private fun compressPhoto(path: String) {
        val imgFile = File(path.substringAfter("file://"))
        val bos = ByteArrayOutputStream()
        val bmp = try {
            val bitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
            val ei = ExifInterface(imgFile.absolutePath)
            when (
                ei.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED
                )
            ) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270f)
                else -> bitmap
            }
        } catch (e: Exception) {
            BitmapFactory.decodeFile(imgFile.absolutePath)
        }
        bmp?.compress(Bitmap.CompressFormat.JPEG, 70, bos)
        val fos = FileOutputStream(imgFile)
        fos.write(bos.toByteArray())
        fos.flush()
        fos.close()
    }

    private fun rotateImage(source: Bitmap, angle: Float): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }

    companion object {
        const val BOOKS_KEY = "books"
        const val USER_ID_KEY = "userId"
        const val ID_KEY = "id"
        const val COVER_URL_KEY = "coverUrl"
    }
}