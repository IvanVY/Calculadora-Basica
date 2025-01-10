package com.example.calculadorabsica

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    // Declaramos las variables para los componentes de la interfaz
    private lateinit var resultadoTextView: TextView  // Para mostrar el resultado
    private var entradaActual = ""  // Para almacenar la entrada del usuario
    private var operador = ""  // Para almacenar el operador seleccionado
    private var primerOperando = 0.0  // Para almacenar el primer operando antes de la operación

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar la vista que mostrará el resultado
        resultadoTextView  = findViewById(R.id.resultadoTextView)

        // Lista de botones numéricos
        val botonesNumericos = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9
        )

        // Asignar los botones de números
        botonesNumericos.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                val numero = (it as Button).text.toString()// Obtener el texto del botón
                appendNumero(numero)  // Llamamos a la función para agregar el número presionado
            }
        }

        // Asignar los botones de operadores
        findViewById<Button>(R.id.buttonAdd).setOnClickListener { setOperador("+") }
        findViewById<Button>(R.id.buttonSubtract).setOnClickListener { setOperador("-") }
        findViewById<Button>(R.id.buttonMultiply).setOnClickListener { setOperador("*") }
        findViewById<Button>(R.id.buttonDivide).setOnClickListener { setOperador("/") }

        // Asignar el botón de igual
        findViewById<Button>(R.id.buttonEqual).setOnClickListener { calcularResultado() }
        // Asignar el botón de borrar
        findViewById<Button>(R.id.buttonClear).setOnClickListener { limpiar() }
    }
    // Función para agregar un número a la entrada actual
    private fun appendNumero(numero: String) {
        entradaActual += numero  // Concatenamos el número al valor actual de entrada
        resultadoTextView.text = entradaActual  // Mostramos la entrada actual en la pantalla
    }

    // Función para configurar el operador seleccionado
    private fun setOperador(op: String) {
        if (entradaActual.isNotEmpty()) {  // Si hay una entrada
            if (primerOperando == 0.0) {  // Si no hay primer operando, lo guardamos
                primerOperando = entradaActual.toDouble()
            } else {
                calcularResultado()  // Si ya hay un primer operando, calculamos el resultado
            }
            operador = op  // Guardamos el operador
            entradaActual = ""  // Limpiamos la entrada para ingresar el segundo operando
        }
    }
    // Función para calcular el resultado de la operación
    private fun calcularResultado() {
        if (entradaActual.isNotEmpty() && operador.isNotEmpty()) {  // Si hay una entrada y un operador
            val segundoOperando = entradaActual.toDouble()  // Guardamos el segundo operando
            val resultado = when (operador) {  // Realizamos la operación según el operador
                "+" -> primerOperando + segundoOperando
                "-" -> primerOperando - segundoOperando
                "*" -> primerOperando * segundoOperando
                "/" -> if (segundoOperando != 0.0) primerOperando / segundoOperando else "Error"  // Si la división es por cero, mostramos "Error"
                else -> "Error"  // Si no hay operador válido
            }
            // Actualizamos el primer operando con el resultado para continuar las operaciones
            primerOperando = resultado.toString().toDouble()  // Guardamos el resultado como primer operando para la siguiente operación
            resultadoTextView.text = resultado.toString()  // Mostramos el resultado en la pantalla
            entradaActual = ""  // Limpiamos la entrada para la siguiente operación
            operador = ""  // Limpiamos el operador
        }
    }
    // Función para limpiar la calculadora
    private fun limpiar() {
        entradaActual = ""  // Limpiamos la entrada
        operador = ""  // Limpiamos el operador
        primerOperando = 0.0  // Restablecemos el primer operando
        resultadoTextView.text = "0"  // Mostramos "0" en la pantalla
    }
}