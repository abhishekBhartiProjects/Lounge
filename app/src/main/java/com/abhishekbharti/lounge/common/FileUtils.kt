package com.abhishekbharti.lounge.common

import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.DatabaseUtils
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import com.abhishekbharti.lounge.LoungeApplication
import com.abhishekbharti.lounge.R
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileFilter
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

object FileUtils {
    private const val DOCUMENTS_DIR = "documents"
    private const val AUTHORITY = "YOUR_AUTHORITY.provider" // configured android:authorities in AndroidManifest (https://developer.android.com/reference/android/support/v4/content/FileProvider)
    private const val HIDDEN_PREFIX = "."
    private const val DEBUG = false // Set to true to enable logging
    private var filesDir: File? = null

    init {
        filesDir = LoungeApplication.getInstance().applicationContext.getExternalFilesDir(
            Environment.DIRECTORY_DOWNLOADS)
    }
    /**
     * File and folder comparator. TODO Expose sorting option method
     */
    var sComparator: Comparator<File> = Comparator { f1, f2 ->
        // Sort alphabetically by lower case, which is much cleaner
        f1.name.lowercase().compareTo(
            f2.name.lowercase()
        )
    }

    /**
     * File (not directories) filter.
     */
    var sFileFilter: FileFilter = FileFilter { file ->
        val fileName = file.name
        // Return files only (not directories) and skip hidden files
        file.isFile && !fileName.startsWith(HIDDEN_PREFIX)
    }

    /**
     * Folder (directories) filter.
     */
    var sDirFilter: FileFilter = FileFilter { file ->
        val fileName = file.name
        // Return directories only and skip hidden directories
        file.isDirectory && !fileName.startsWith(HIDDEN_PREFIX)
    }

    private val availableSpaceInMB: Long
        get() {
            return if (isExternalStorageAvailable && !isExternalStorageReadOnly && filesDir?.exists() == true) {
                val sizeKB = 1024L
                val sizeMB = sizeKB * sizeKB
                val availableSpace: Long
                val stat = StatFs(filesDir?.path)
                availableSpace = stat.availableBlocksLong * stat.blockSizeLong
                availableSpace / sizeMB
            } else {
                -1L
            }
        }

    private val isExternalStorageAvailable: Boolean
        get() {
            val extStorageState = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == extStorageState
        }

    private val isExternalStorageReadOnly: Boolean
        get() {
            val extStorageState = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED_READ_ONLY == extStorageState
        }

    /**
     * Gets the extension of a file name, like ".png" or ".jpg".
     *
     * @param uri
     * @return Extension including the dot("."); "" if there is no extension;
     * null if uri was null.
     */
    fun getExtension(uri: String?): String? {
        if (uri == null) {
            return null
        }
        val dot = uri.lastIndexOf(".")
        return if (dot >= 0) {
            uri.substring(dot)
        } else {
            // No extension.
            ""
        }
    }

    /**
     * @return Whether the URI is a local one.
     */
    private fun isLocal(url: String?): Boolean {
        return url != null && !url.startsWith("http://") && !url.startsWith("https://")
    }


    /**
     * Convert File into Uri.
     *
     * @param file
     * @return uri
     */
    fun getUri(file: File?): Uri? {
        return if (file != null) {
            Uri.fromFile(file)
        } else null
    }

    /**
     * Returns the path only (without file name).
     *
     * @param file
     * @return
     */
    fun getPathWithoutFilename(file: File?): File? {
        if (file != null) {
            if (file.isDirectory) {
                // no file to be split off. Return everything
                return file
            } else {
                val filename = file.name
                val filepath = file.absolutePath

                // Construct path without file name.
                var pathwithoutname = filepath.substring(
                    0,
                    filepath.length - filename.length
                )
                if (pathwithoutname.endsWith("/")) {
                    pathwithoutname = pathwithoutname.substring(0, pathwithoutname.length - 1)
                }
                return File(pathwithoutname)
            }
        }
        return null
    }

    /**
     * @return The MIME type for the given file.
     */
    fun getMimeType(file: File): String? {

        val extension = getExtension(file.name)

        return if (extension!!.isNotEmpty()) MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.substring(1)) else "application/octet-stream"

    }

    /**
     * @return The MIME type for the give Uri.
     */
    fun getMimeType(context: Context, uri: Uri): String? {
        val file = File(getPath(context, uri))
        return getMimeType(file)
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is local.
     */
    private fun isLocalStorageDocument(uri: Uri): Boolean {
        return AUTHORITY == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    fun isGoogleDrive(uri: Uri): Boolean {
        return "com.google.android.apps.docs.storage" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private fun getDataColumn(
        context: Context, uri: Uri?, selection: String?,
        selectionArgs: Array<String>?
    ): String? {

        var cursor: Cursor? = null
        val column = MediaStore.Files.FileColumns.DATA
        val projection = arrayOf(column)

        try {
            cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                if (DEBUG)
                    DatabaseUtils.dumpCursor(cursor)

                val columnIndex = cursor.getColumnIndex(column)
                if (columnIndex > -1) {
                    return cursor.getString(columnIndex)
                } else {
                    return null
                }
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.<br></br>
     * <br></br>
     * Callers should check whether the path is local before assuming it
     * represents a local file.
     *
     * @param context The context.
     * @param uri     The Uri to query.
     * @see .isLocal
     * @see .getFile
     */
    fun getPath(context: Context, uri: Uri): String? {

//        if (DEBUG)
//            Timber.d(
//                """Authority: ${uri.authority}, Fragment: ${uri.fragment}, Port: ${uri.port}, Query: ${uri.query}, Scheme: ${uri.scheme}, Host: ${uri.host}, Segments: ${uri.pathSegments}"""
//            )

        val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
        // content://com.google.android.apps.docs.storage/document/acc=1;doc=encoded=X8mOUUhHoGMFQtK0OI66FjLvwxGHdhIiUEEu7BdeysJkeDWPFum1GU8n
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // LocalStorageProvider
            if (isLocalStorageDocument(uri)) {
                // The path is the id
                return DocumentsContract.getDocumentId(uri)
            } else if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }
            } else if (isDownloadsDocument(uri)) {

                val id = DocumentsContract.getDocumentId(uri)

                if (id != null && id.startsWith("raw:")) {
                    return id.substring(4)
                }

                val contentUriPrefixesToTry =
                    arrayOf("content://downloads/public_downloads", "content://downloads/my_downloads")

                for (contentUriPrefix in contentUriPrefixesToTry) {
                    try {
                        val contentUri =
                            ContentUris.withAppendedId(Uri.parse(contentUriPrefix), java.lang.Long.valueOf(id!!))
                        val path = getDataColumn(context, contentUri, null, null)
                        if (!path.isNullOrEmpty()) {
//                            Timber.d( "%s - okay i got a path", path!!)
                            return path
                        }
                    } catch (e: Exception) {
                    }

                }

                // path could not be retrieved using ContentResolver, therefore copy file to accessible cache using streams
                val fileName = getFileName(context, uri)
                val cacheDir = getDocumentCacheDir(context)
                val file = generateFileName(fileName, cacheDir)
                var destinationPath: String? = null
                if (file != null) {
                    destinationPath = file.absolutePath
                    saveFileFromUri(context, uri, destinationPath)
                }

                return destinationPath
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                var contentUri: Uri? = null
                when (type) {
                    "image" -> {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    }
                    "video" -> {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    }
                    "audio" -> {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                }

                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])

                return getDataColumn(context, contentUri, selection, selectionArgs)
            }
            // MediaProvider
            // DownloadsProvider
            // ExternalStorageProvider
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {

            // Return the remote address
            return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(context, uri, null, null)

        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }// File
        // MediaStore (and general)

        return null
    }

    /**
     * Convert Uri into File, if possible.
     *
     * @return file A local file that the Uri was pointing to, or null if the
     * Uri is unsupported or pointed to a remote resource.
     * @author paulburke
     * @see .getPath
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    fun getFile(context: Context, uri: Uri?): File? {
        if (uri != null) {
            val path = getPath(context, uri)
            if (path != null && isLocal(path)) {
                return File(path)
            }
        }
        return null
    }

    /**
     * Get the Intent for selecting content to be used in an Intent Chooser.
     *
     * @return The intent for opening a file with Intent.createChooser()
     */
    fun createGetContentIntent(): Intent {
        // Implicitly allow the user to select a particular kind of data
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        // The MIME data type filter
        intent.type = "*/*"
        // Only return URIs that can be opened with ContentResolver
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        return intent
    }


    private fun getDocumentCacheDir(@NonNull context: Context): File {
        val dir = File(context.cacheDir, DOCUMENTS_DIR)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        logDir(context.cacheDir)
        logDir(dir)

        return dir
    }

    private fun logDir(dir: File) {
        if (!DEBUG) return
//        Timber.d( "Dir=$dir")
        val files = dir.listFiles()
        for (file in files) {
//            Timber.d("File=%s", file.path)
        }
    }

    @Nullable
    fun generateFileName(@Nullable name: String?, directory: File): File? {
        var name: String? = name ?: return null

        var file = File(directory, name)

        if (file.exists()) {
            var fileName: String = name!!
            var extension = ""
            val dotIndex = name.lastIndexOf('.')
            if (dotIndex > 0) {
                fileName = name.substring(0, dotIndex)
                extension = name.substring(dotIndex)
            }

            var index = 0

            while (file.exists()) {
                index++
                name = "$fileName($index)$extension"
                file = File(directory, name)
            }
        }

        try {
            if (!file.createNewFile()) {
                return null
            }
        } catch (e: IOException) {
//            Timber.w(e)
            return null
        }

        logDir(directory)

        return file
    }

    private fun saveFileFromUri(context: Context, uri: Uri, destinationPath: String?) {
        var `is`: InputStream? = null
        var bos: BufferedOutputStream? = null
        try {
            `is` = context.contentResolver.openInputStream(uri)
            bos = BufferedOutputStream(FileOutputStream(destinationPath, false))
            val buf = ByteArray(1024)
            `is`!!.read(buf)
            do {
                bos.write(buf)
            } while (`is`.read(buf) != -1)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                `is`?.close()
                bos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    @Throws(IOException::class)
    fun createTempImageFile(context: Context, fileName: String): File {
        // Create an image file name
        val storageDir = File(context.cacheDir, DOCUMENTS_DIR)
        return File.createTempFile(fileName, ".jpg", storageDir)
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    fun getFileName(@NonNull context: Context, uri: Uri): String? {
        val mimeType = context.contentResolver.getType(uri)
        var filename: String? = null

        if (mimeType == null && context != null) {
            val path = getPath(context, uri)
            filename = if (path == null) {
                getName(uri.toString())
            } else {
                val file = File(path)
                file.name
            }
        } else {
            val returnCursor = context.contentResolver.query(uri, null, null, null, null)
            if (returnCursor != null) {
                val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                returnCursor.moveToFirst()
                filename = returnCursor.getString(nameIndex)
                returnCursor.close()
            }
        }

        return filename
    }

    fun getName(filename: String?): String? {
        if (filename == null) {
            return null
        }
        val index = filename.lastIndexOf('/')
        return filename.substring(index + 1)
    }

    fun getOfflineEpisodeFilePathIfExists(episodeId: Int, context: Context): String? {
        var filePath: String? = null
        val contents = getEpisodeDownloadDirectory(context).listFiles()
        if (contents.size > 0) {
            for (f in contents) {
                if (f.name.toLowerCase().contains(episodeId.toString())) {
                    filePath = f.path
                    break
                }
            }
        }
        return filePath
    }

    private fun getEpisodeDownloadDirectory(context: Context): File {
        val videoDirectory = File(context.applicationContext.filesDir, context.getString(R.string.app_name))
        if (!videoDirectory.exists()) {
            videoDirectory.mkdirs()
        }
        return videoDirectory
    }

    fun getAppsFileDirectory(context: Context): File {
        val directory = File(context.filesDir, context.getString(R.string.app_name))
        if (!directory.exists()) {
            directory.mkdirs()
        }
        return directory
    }

//    fun getCUPartDirectory(context: Context, entity: ContentUnitPartEntity?): File {
//        val directory = getAppsFileDirectory(context)
//        val showDirectory = File(directory, entity?.showSlug?: entity?.slug.toString())
//
//        if (!showDirectory.exists()) {
//            showDirectory.mkdirs()
//        }
//
//        val cuPartDirectory = File(showDirectory, entity?.id.toString())
//
//        if (!cuPartDirectory.exists()) {
//            cuPartDirectory.mkdirs()
//        }
//
//        return cuPartDirectory
//    }

    fun getShowDirectory(context: Context, showSlug: String): File {
        val directory = getAppsFileDirectory(context)
        val showDirectory = File(directory, showSlug)

        if (!showDirectory.exists()) {
            showDirectory.mkdirs()
        }
        return showDirectory
    }

    fun deleteDirectoryAndItsContent(directory: File) {
        if (directory.exists()) {
            if (directory.isDirectory) {
                for (child in directory.listFiles())
                    deleteDirectoryAndItsContent(child)
            }
            directory.delete()
        }
    }

    fun isEnoughSpaceAvailable(currentFileLength: Long): Boolean {
        val spaceAvailable = availableSpaceInMB
        val kb = calculateFileSizeInKb(currentFileLength)
        val fileSize = calculateFileSizeInMb(kb)
        return spaceAvailable >= fileSize
    }

    fun checkAndGetAudioUri(audioFile: Uri?, context: Context): Uri? {
        var file = File(audioFile.toString())
        if (audioFile != null && File(audioFile?.path).path.split("/")[1].contains("root_path")) {
            file = File(File(audioFile?.path).path.removePrefix("/root_path"))
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && audioFile != null) {
                file = File(getPath(context, audioFile))
            }
        }
        return if (audioFile != null && File(file.path).exists() && File(file.path).length() > 0) {
            Uri.parse(file.path)
        } else {
            null
        }
    }

    fun getPublicVideoStorageDirectory(context: Context): File {
        return File(
            context.getExternalFilesDir(Environment.DIRECTORY_MOVIES), context.getString(
                R.string.app_name
            )
        )
    }

    fun calculateFileSizeInKb(fileSizeInBytes: Long): Long {
        return fileSizeInBytes / 1024
    }

    fun calculateFileSizeInMb(fileSizeInMb: Long): Long {
        return fileSizeInMb / 1024
    }

}