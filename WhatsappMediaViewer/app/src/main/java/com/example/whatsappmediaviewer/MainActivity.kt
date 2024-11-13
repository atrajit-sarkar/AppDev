package com.example.whatsappmediaviewer

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.documentfile.provider.DocumentFile
import coil.compose.rememberAsyncImagePainter
import com.example.whatsappmediaviewer.ui.theme.WhatsappMediaViewerTheme
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WhatsappMediaViewerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(horizontal = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        FolderPickerExample()
                    }
                }
            }
        }
    }
}


@Composable
fun FolderPickerExample() {
    val context = LocalContext.current
    var fileList = remember { mutableStateOf<List<DocumentFile>>(emptyList()) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree()
    ) { uri ->
        if (uri != null) {
            Log.d("Uri", "FolderPickerExample: $uri")
            val folderName = DocumentFile.fromTreeUri(context, uri)?.name
            Toast.makeText(context, "Folder Selected: $folderName", Toast.LENGTH_SHORT).show()
            // Use this Uri to list or access files in the folder
            fileList.value = accessFolderWithDocumentFile(context, uri) ?: emptyList()
            fileList.value.forEach { file ->
                Log.d("FolderPickerExample", "File: ${file.name}")
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(onClick = { launcher.launch(null) }) {
            Text("Pick Folder")
        }


        FileListScreen(files = fileList.value, context)
    }

}


fun accessFolderWithDocumentFile(context: Context, uri: Uri): List<DocumentFile>? {
    val folder = DocumentFile.fromTreeUri(context, uri)
    return folder?.listFiles()?.toList()
}


@Composable
fun FileListScreen(files: List<DocumentFile>, context: Context) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(files) { file ->
            FileItem(file, context)
            HorizontalDivider()
        }
    }
}

@Composable
fun FileItem(file: DocumentFile, context: Context) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .wrapContentSize()
                .clickable { openFile(context, file) },
            elevation = CardDefaults.cardElevation(12.dp),
            border = BorderStroke(1.dp, color = Color.LightGray),
//            colors = CardDefaults.cardColors(Color())
        ) {


            when {
                file.type?.startsWith("image/") == true -> {
                    ImagePreview(file)
                }
                file.type == "text/plain" -> {
                    TextPreview(file, context)
                }
                file.type == "application/pdf" -> {
                    PdfPreview(file, context)
                }
                file.type?.startsWith("video/") == true -> {
                    Mp4Preview(file, context) // Add MP4 check here
                }
                else -> {
                    FaIcon(
                        faIcon = FaIcons.File
                    )
                }
            }

        }

        Spacer(modifier = Modifier.width(16.dp))
        Card(
            modifier = Modifier.width(170.dp),

        ) {

            Text(
                text = file.name ?: "Unknown File",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}


fun openFile(context: Context, file: DocumentFile) {
    val uri = file.uri
    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, file.type)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "No app found to open this file type", Toast.LENGTH_SHORT).show()
    }
}

// Preview Functions.............
@Composable
fun ImagePreview(file: DocumentFile) {
    val painter = rememberAsyncImagePainter(model = file.uri)
    Image(
        painter = painter,
        contentDescription = "Image Preview",
        modifier = Modifier.size(200.dp)
    )
}

@Composable
fun TextPreview(file: DocumentFile, context: Context) {
    val textSnippet = remember(file.uri) {
        context.contentResolver.openInputStream(file.uri)?.bufferedReader()?.use {
            it.readLine()?.take(20) + "..." // Show first 20 characters
        }
    }

    Text(
        text = textSnippet ?: "Preview unavailable",
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier.size(40.dp)
    )
}

@Composable
fun PdfPreview(file: DocumentFile, context: Context) {
    val bitmap = remember(file.uri) {
        val pdfRenderer = PdfRenderer(
            context.contentResolver.openFileDescriptor(file.uri, "r")!!
        )
        val page = pdfRenderer.openPage(0)
        val bmp = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
        page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        page.close()
        pdfRenderer.close()
        bmp
    }

    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = "PDF Preview",
        modifier = Modifier.size(40.dp)
    )
}

@Composable
fun Mp4Preview(file: DocumentFile, context: Context) {
    val thumbnail = remember(file.uri) {
        // Using MediaMetadataRetriever to get the first frame of the video
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, file.uri)
        val bitmap = retriever.getFrameAtTime(1000) // Get the first frame (1 second mark)
        retriever.release()
        bitmap
    }

    if (thumbnail != null) {
        Image(
            bitmap = thumbnail.asImageBitmap(),
            contentDescription = "MP4 Preview",
            modifier = Modifier.size(200.dp) // You can adjust the size as needed
        )
    } else {
        FaIcon(
            faIcon = FaIcons.FileVideo
        )
    }
    Text("Video",
        fontStyle = FontStyle.Italic,
        textAlign = TextAlign.Center,
        modifier = Modifier.width(200.dp),
        textDecoration = TextDecoration.Underline
    )
}


