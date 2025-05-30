package com.example.loteria_app.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.loteria_app.R

@Composable
fun LoNumberTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Next,
    @StringRes label: Int,
    @StringRes placeholder: Int

) {

    OutlinedTextField(
        modifier = modifier,
        value = value,
        maxLines = 1,
        label = {
            Text(text = stringResource(id = label))
        },
        placeholder = { Text(text = stringResource(id = placeholder)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        ),
        onValueChange = onValueChange
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoTextDropDown(
    modifier: Modifier = Modifier,
    value: String,
    onSelect:(String)->Unit,
    list: List<String>,
    label: String,
) {

    var dropDownExpanded by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf(TextFieldValue(value)) }

    ExposedDropdownMenuBox(
        expanded = dropDownExpanded,
        onExpandedChange = {
            dropDownExpanded = !dropDownExpanded
        }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            value = textFieldValue,
            onValueChange = {},
            readOnly = true,
            label = { Text(text = label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropDownExpanded)
            }
        )

        ExposedDropdownMenu(
            expanded = dropDownExpanded,
            onDismissRequest = {
                dropDownExpanded = false
            }
        ) {

            list.forEach {

                DropdownMenuItem(text = {
                    Text(it)
                }, onClick = {
                    textFieldValue = TextFieldValue(it)
                    onSelect(it)
                    dropDownExpanded = false
                })
            }
        }
    }

}

@Preview
@Composable
private fun AutoTextDropDownPreview() {
    AutoTextDropDown(list = listOf(), label = "Teste", value = "", onSelect = {})
}


@Preview
@Composable
private fun LoNumberTextFieldPreview() {

    LoNumberTextField(
        label = R.string.quantity,
        onValueChange = {},
        value = "",
        placeholder = R.string.mega_rule

    )
}

@Composable
fun LoItemType(
    modifier: Modifier = Modifier,
    name: String,
    @DrawableRes icon: Int = R.drawable.trevo,
    color: Color = Color.Black,
    bgColor: Color = Color.Transparent
) {

    Column(
        modifier = modifier
            .wrapContentSize()
            .background(bgColor),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Image(
            modifier = Modifier
                .size(100.dp)
                .padding(10.dp),
            painter = painterResource(id = icon),
            contentDescription = stringResource(id = R.string.trevo)
        )

        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            color = color,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoItemTypePreview() {
    LoItemType(name = "Mega Sena")
}