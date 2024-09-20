package com.example.studentapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentapp.datas.Clas
import com.example.studentapp.datas.DiaTarea
import com.example.studentapp.ui.theme.StudentAppTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudentAppTheme {
                Menu()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
//@Preview
@Composable
fun Menu() {
    val snackbarHostState = remember { SnackbarHostState() }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(onClick = { /* Acción del ícono de navegación */ }) {
                        Icon(
                            imageVector = Icons.Default.AccountBox,
                            contentDescription = "Menu Icon"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Acción del ícono de búsqueda */ }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                    }
                },
                title = {
//                    Text("Bienvenido", textAlign = TextAlign.Center,)
                    CustomH2Text(text = "Bienvenido")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                /* Acción del botón flotante */
//TODO poner modalBottomSheet
                showBottomSheet = true;
            }) {
//                Text(text = "Nueva tarea")
                Row(
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Nueva tarea",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Nueva tarea", color = Color.White)
                }
            }
        }, bottomBar = {
            barraInferior()
        },
        content = { innerPadding ->
            contentMain(modifier = Modifier.padding(innerPadding), clik = {showBottomSheet = true;Log.e("MIRA","EAS")});
            if (showBottomSheet) {
                ModalBottomSheet(
                    modifier = Modifier.fillMaxSize(),
                    sheetState = sheetState, onDismissRequest = { showBottomSheet = false }) {
                    barraMateriaVistaInferior();

                }
            }
        }
    )
}

@Composable
fun barraInferior() {
    BottomAppBar(
        containerColor = Color.Transparent, // Color de fondo del BottomAppBar
        tonalElevation = 0.dp, modifier = Modifier.padding(10.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f, true))
        BottomBarItem(
            icon = Icons.Default.Home,
            label = "Inicio",
            onClick = { /* Acción de Inicio */ }
        )
        BottomBarItem(
            icon = Icons.Default.Search,
            label = "Buscar",
            onClick = { /* Acción de Buscar */ }
        )
        BottomBarItem(
            icon = Icons.Default.AccountCircle,
            label = "Perfil",
            onClick = { /* Acción de Perfil */ }
        )
        Spacer(modifier = Modifier.weight(1f, true))
    }
}

@Composable
fun BottomBarItem(icon: ImageVector, label: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color.White
            )
        }
        Text(
            text = label,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

//@Preview
@Composable
fun contentMain(modifier: Modifier, clik: () -> Unit) {
    var materias = listOf(
        Clas("Fisica 1", "12-15 Martes"),
        Clas("Analisis matematico 1", "15-18 Jueves"),
        Clas("Algebra", "08-12 Lunes")
    );

    Column(
        modifier = modifier
    ) {
        targetaPrincipal(modifier);
        chipsCalendario();
        CustomH2Text("Tu materias");
        LazyColumn {
            items(materias) { materia ->
                targetaDia(clik = clik, nombreMateria = materia.materia, horario = materia.horario);
            }
        }
    }
}

@Preview
@Composable
//@Composable
fun barraMateriaVistaInferior(
    nombreMateria: String = "Fisica 1",
    temario: List<String> = listOf(
        "Cinemática",
        "Dinámica",
        "Trabajo y Energía",
        "Momentum (Cantidad de Movimiento)",
        "Gravitación",
        "Oscilaciones y Ondas",
        "Fluidos",
        "Termodinámica"
    )
) {
    Box(modifier = Modifier.padding(10.dp)) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = 16.dp)) {
                CustomH2Text(text = nombreMateria)
                LazyColumn {
                    items(temario) { tem ->
                        Text(text = tem)
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                }
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp), onClick = { /*TODO*/ }) {
                    Text(text = "Ver apuntes")
                }
            }
        }
    }
}

@Composable
fun chipsCalendario() {
    var diaTema= listOf(DiaTarea("Lunes",0),DiaTarea("Martes",1),DiaTarea("Miercoles",3),DiaTarea("Jueves",0),DiaTarea("Viernes",0),DiaTarea("Sabado",0))
    val dias = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado")

    LazyRow(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        items(diaTema) { dia ->
            chipsDia(dia.dia, tarea = dia.tarea);
            Spacer(modifier = Modifier.width(5.dp));
        }
    }
}

@Composable
fun chipsDia(texto: String = "Dia",tarea:Int=0) {
    SuggestionChip(
        onClick = { Log.d("Suggestion chip", "hello world") },
        label = {
            Column(modifier = Modifier.padding(1.dp)) {
                if(tarea.equals(0)){
                    Text(texto);

                }
                else{
                    Text(texto +" tareas : $tarea")
                }
//                Text(texto + (tarea!=0?(" tareas $tarea")));
            }
        }

    )
}

//@Composable
@Composable
fun targetaPrincipal(modifier: Modifier) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(2.dp, Color.Black),
        modifier = modifier
            .fillMaxHeight(0.2f) // Ajusta la altura del Card
            .fillMaxWidth(1f) // Ajusta el ancho del Card
            .padding(horizontal = 8.dp, vertical = 2.dp) // Ajusta el padding alrededor del Card
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize() // Ocupa todo el espacio del Card
                .padding(horizontal = 16.dp, vertical = 0.dp), // Ajusta el padding interno
            verticalArrangement = Arrangement.Center, // Centra el contenido verticalmente
            horizontalAlignment = Alignment.CenterHorizontally // Centra el contenido horizontalmente
        ) {
            Text(
                text = "Tareas pendientes",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 0.dp) // Espaciado entre los textos
            )
            Text(
                text = "Buena cursada ;)",
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun targetaDia(
    nombreMateria: String = "FISICA",
    horario: String = "Lunes 18:00",
    cardHeight: Dp = 200.dp,
    clik: () -> Unit
) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary // Establece el color de fondo
        ),
        onClick = {clik},
        border = BorderStroke(2.dp, Color.Black),
        modifier = Modifier
            .height(cardHeight) // Establece la altura fija
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp) // Ajusta el padding alrededor del Card
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .align(Alignment.TopStart)
            ) {
                Text(
                    text = nombreMateria, style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Spacer(modifier = Modifier.height(10.dp)) // Espacio vertical entre el nombre y el horario
                Text(text = horario)
                Spacer(modifier = Modifier.height(10.dp)) // Espacio vertical adicional
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart) // Alinea barritaProfesor en la parte inferior
                    .fillMaxWidth() // Ocupa toda la anchura disponible
                    .padding(
                        horizontal = 10.dp,
                        vertical = 0.dp
                    ) // Padding adicional para barritaProfesor
            ) {
                barritaProfesor(nombreProfesor = "Trujillo", tareasPendientes = "1")
            }
        }
    }
}

@Preview
@Composable
fun barritaProfesor(
    nombreProfesor: String = "Trujillo",
    tareasPendientes: String = "1"
) {
    // Estado que controla la posición de la animación
    val offsetX = remember { Animatable(-250f) } // Comienza fuera de la pantalla
    LaunchedEffect(Unit) {
        offsetX.animateTo(
            targetValue = 0f, // Mueve el elemento a su posición final
            animationSpec = tween(
                durationMillis = 1500,
                easing = FastOutSlowInEasing
            )
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .offset(x = offsetX.value.dp) // Aplicamos la animación de deslizamiento
            .fillMaxWidth()
    ) {
        IconButton(onClick = { /* Acción del ícono de búsqueda */ }) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Search Icon",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = nombreProfesor,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = tareasPendientes,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}


@Composable
fun CustomH2Text(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 24.sp, // Tamaño grande similar a H2
            fontWeight = FontWeight.Bold, // Peso del texto en negrita
            color = MaterialTheme.colorScheme.onBackground, // Color del texto acorde al tema
            lineHeight = 32.sp // Espaciado entre líneas para mejorar la legibilidad
        ),
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp) // Padding para separar el texto del borde
    )
}