package com.sgtech.noteapp.presentation.add

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sgtech.noteapp.MainViewModel
import com.sgtech.noteapp.data.local.entities.Note
import com.sgtech.noteapp.presentation.add.components.AddTopBar

@Composable
fun AddScreen(
    id: Int? = null,
    navHostController: NavHostController,
    mainViewModel: MainViewModel
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val context = LocalContext.current
    var note: Note? = null
    if (id != null) {
        note = mainViewModel.getNoteById(id).collectAsState(initial = null).value
        title = note?.title.toString()
        description = note?.description.toString()
    }

    val focusManager = LocalFocusManager.current
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        AddTopBar(title = if (note != null) "Update Note" else "Add Note", onBack = {
            navHostController.popBackStack()
        }) {
            if (title.isNotEmpty() && description.isNotEmpty()) {

                if (note != null) {
                    mainViewModel.updateNote(
                        note.copy(title = title, description = description)
                    )
                    Toast.makeText(context, "Note Updated", Toast.LENGTH_SHORT).show()
                    navHostController.popBackStack()
                } else {
                    mainViewModel.addNote(
                        Note(
                            title = title,
                            description = description,
                            imgUrl = "",
                            date = System.currentTimeMillis().toString(),
                            isFavorite = false
                        )
                    )
                    Toast.makeText(context, "Note Added", Toast.LENGTH_SHORT).show()

                }

                title = ""
                description = ""

            }


        }
    }) { it ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { value ->
                    title = value
                },
                singleLine = true,
                maxLines = 1,
                placeholder = {
                    Text(text = "Title")
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(0.dp, MaterialTheme.colorScheme.background))
                    .padding(8.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.0f),
                    unfocusedBorderColor = MaterialTheme.colorScheme.background,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
                    focusedContainerColor = MaterialTheme.colorScheme.onSurface.copy(0.1f),
                    unfocusedContainerColor = MaterialTheme.colorScheme.background
                )
            )

            OutlinedTextField(
                value = description,
                onValueChange = {
                    description = it
                },
                placeholder = {
                    Text(text = "Description")
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                    if (title.isNotEmpty() && description.isNotEmpty()) {
                        if (note != null) {
                            mainViewModel.updateNote(
                                note.copy(
                                    title = title, description = description
                                )
                            )
                            Toast.makeText(context, "Note Update", Toast.LENGTH_SHORT).show()

                        } else {
                            mainViewModel.addNote(
                                Note(
                                    title = title,
                                    description = description,
                                    date = System.currentTimeMillis().toString(),
                                    imgUrl = "",
                                    isFavorite = false
                                )
                            )
                            Toast.makeText(context, "Note Added", Toast.LENGTH_SHORT).show()

                        }
                        title = ""
                        description = ""
                    }
                }),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.0f),
                    unfocusedBorderColor = MaterialTheme.colorScheme.background,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
                    focusedContainerColor = MaterialTheme.colorScheme.onSurface.copy(0.1f),
                    unfocusedContainerColor = MaterialTheme.colorScheme.background
                )
            )


        }
    }
}