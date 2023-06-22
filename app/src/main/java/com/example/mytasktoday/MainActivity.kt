package com.example.mytasktoday

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytasktoday.model.Tarefa.Tarefa
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreenContent(DrawerState(initialValue = DrawerValue.Closed))
        }
    }
}

@Composable
fun MainScreenContent(drawerState: DrawerState){
    val scaffoldState = rememberScaffoldState(drawerState = drawerState)
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = (Color(0xFF32383D)),
                contentColor = Color.Black,
                title = { Text(text = "TaskTodayApp", color = Color(0xFF3F51B5))},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            tint = Color(0xFF3F51B5),
                            contentDescription = "Drawer Menu"
                        )
                    }
                }
            )
        },
        drawerBackgroundColor = (Color(0xFF32383D)),
        drawerGesturesEnabled = drawerState.isOpen,
        drawerContent = {
            Box(
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .background(color = Color(0xFF32383D))
                    .height(50.dp)
                    .padding(5.dp)
            )
        },
        content = {
                paddingValues -> Log.i("paddingValues", "$paddingValues")
            Column(
                modifier = Modifier
                    .background(color = Color(0xFF222629))
                    .fillMaxSize()
            ) {
                MySearchField(modifier = Modifier.fillMaxWidth())

                val tPDTCC = Tarefa(
                    "Matéria do TCC",
                    "Realizar Monografia",
                    Date(),
                    Date(),
                    status = 0.0
                )

                val tAtividadeMAT = Tarefa(
                    "Atividade de Matemática",
                    "Realizar o calculo de uma sombra",
                    Date(),
                    Date(),
                    status = 0.0
                )

                val tAtividadePAM = Tarefa(
                    "Atividade de PAM",
                    "Revisar uma tarefa",
                    Date(),
                    Date(),
                    status = 0.0
                )


                val minhaListaDeTarefas = listOf(tPDTCC, tAtividadeMAT, tAtividadePAM,)

                MyTaskWidgetList(minhaListaDeTarefas)
            }//Column
        },//content
        bottomBar = {
            BottomAppBar(
                backgroundColor = (Color(0xFF32383D)),
                contentColor = Color(0xFFE91E63),
                contentPadding = PaddingValues(7.dp),
                content = {
                    Text("BIANCA FARIAS DA SILVA", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(130.dp))
                    Text("RM: 22315", fontWeight = FontWeight.Bold)
                }
            )
        },

        isFloatingActionButtonDocked = false,
        floatingActionButton = { ExtendedFloatingActionButton(
            backgroundColor = (Color(0xFF32383D)),
            contentColor = Color(0xFFE91E63),
            icon = {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Adicionar Tarefa"
                )
            },
            text = { Text("ADD")  },
            onClick = { /*TODO*/ })

        }
    ) //Scaffold
} //MainScreenContent

@Composable
fun MyTaskWidgetList(listaDeTarefas: List<Tarefa>){
    listaDeTarefas.forEach(
        action = { MyTaskWidget(modifier = Modifier.fillMaxWidth(), tarefasMostrada = it) }
    )
} //MyTaskWidgetList

@Composable
fun MySearchField(modifier: Modifier){
    TextField(
        value = "",
        onValueChange = {},
        modifier = modifier,
        placeholder = { Text(text = "Pesquisar tarefas", color = Color(0xFFE91E63))},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = Color(0xFFE91E63),
                contentDescription = "Search Icon")
        }
    )
} //MySearchField

@SuppressLint("SuspiciousIndentation")
@Composable
fun MyTaskWidget(
    modifier: Modifier,
    tarefasMostrada: Tarefa
){
    val dateFormatter = SimpleDateFormat("EEE, MMM dd, yyyy", Locale.getDefault())

    Column(modifier = modifier
        //.border(width = 0.5.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))
        .padding(3.dp)
    ){
        Row(modifier = modifier.background(Color(0xFF32383D))) {
            Column(modifier = Modifier.width(150.dp).padding(10.dp)){
                Icon(
                    imageVector = Icons.Default.Notifications,
                    tint = Color(0xFFE91E63),
                    contentDescription = "Icons of a pendent task")
                Text(
                    text = dateFormatter.format(tarefasMostrada.pzoFinal),
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                    color = Color(0xFFE91E63),
                    fontSize = 12.sp
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .clickable{ },
                elevation = 10.dp,
                backgroundColor = Color(0xFF32383D)
            ) {
                Column {
                    Text(
                        text = tarefasMostrada.nome,
                        fontSize = 20.sp,
                        color = Color(0xFFE91E63),
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    )

                    Text(
                        text = tarefasMostrada.detalhes,
                        fontSize = 12.sp,
                        color = Color(0xFFE91E63),
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

    }
    Spacer(modifier = Modifier.height(16.dp))
} //MyTaskWidget


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreenContent(DrawerState(initialValue = DrawerValue.Closed))
}