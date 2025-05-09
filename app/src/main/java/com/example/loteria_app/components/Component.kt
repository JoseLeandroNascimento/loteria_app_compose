package com.example.loteria_app.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    name:String,
    color:Color = Color.Black,
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
            painter = painterResource(id = R.drawable.trevo),
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