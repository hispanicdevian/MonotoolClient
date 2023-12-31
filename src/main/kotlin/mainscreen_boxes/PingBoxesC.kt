package mainscreen_boxes

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import custom_resources.CustomGrayA
import custom_resources.CustomRedA
import custom_resources.MainColorA
import engine_logic.Navi
import engine_logic.SLOnOffHandlerC
import engine_logic.loadFontSizeV1A
import sub_views.settingFontSize
import sub_views.settingOnOffBoxes
import sub_views.settingPingBoxes
import views.settingScreen

//////////////////////////////////////////////////////////// Ping Boxes Shown in the Third Column of the Main Screen
@Composable
@Preview
fun pingBoxesC(pingSuccessfulC8: Boolean, pingSuccessfulC9: Boolean, pingSuccessfulC10: Boolean, pingSuccessfulC11: Boolean) {
// Ram for active View/Screen
    val currentScreen by remember { mutableStateOf<Navi>(Navi.MainScn) }
// Pass through ram for ping state
    val pingSuccessfulList = listOf(pingSuccessfulC8, pingSuccessfulC9, pingSuccessfulC10, pingSuccessfulC11)
// Title list ram, need to be replaced so that the user can modify it live
    val titleList = listOf("8", "9", "10", "11")
// Font size ram
    val fontSizedA by remember { mutableStateOf(loadFontSizeV1A().sp) }
// Loads the last state of On/Off settings
    val visibilityList = remember {
        val currentState = SLOnOffHandlerC.loadOnOffFileC()
        if (currentState.isNotEmpty()) {
            currentState.split(",").map { it.toBoolean() }
        } else {
            listOf(true, true, true, true)
        }
    }
//////////////////////////////////////////////////////////// UI container
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(15.dp))
//////////////////////////////////////////////////////////// Navi head
        when (currentScreen) {
            is Navi.MainScn -> {
////////////////////////////// Creates boxes based on # of titles, and its visibility based on settings file
                for (index in titleList.indices) {
                    if (visibilityList[index]) {
                        Box(
                            modifier = Modifier
                                /*
                                .background(color = ErgoGray, shape = AbsoluteRoundedCornerShape(8.dp))
                                .padding(5.dp)
                                */
                                .weight(1f)
                                .aspectRatio(1.5f)
                                .background((if (pingSuccessfulList[index]) MainColorA else CustomRedA), shape = AbsoluteRoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = titleList[index],
                                    color = CustomGrayA,
                                    fontWeight = FontWeight.W900,
                                    fontSize = fontSizedA
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = if (pingSuccessfulList[index]) "On" else "Off",
                                    color = CustomGrayA,
                                    fontWeight = FontWeight.W800,
                                    fontSize = fontSizedA
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                }
            }
//////////////////////////////////////////////////////////// Navi tail
            Navi.SettingFontSz -> settingFontSize()
            Navi.SettingPingBxs -> settingPingBoxes()
            Navi.SettingScn -> settingScreen()
            Navi.SettingOnOffBxs -> settingOnOffBoxes()
        }
    }
}
