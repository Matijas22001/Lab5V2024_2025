package com.example.lab5v2024_2025

import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import android.opengl.GLU
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/* Implementacja renderera - malarza na płótnie */

class MyRenderer : /*dziedziczenie*/GLSurfaceView.Renderer {
    private var angle=0f; //kąt do obracania figury na płótnie

    //funkcja wywoływana przy tworzeniu płótna
    override fun onSurfaceCreated(gl: GL10?, p1: EGLConfig?) {

    }

    //funkcja wywoływana przy zmianie i tworzeniu płótna
    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        /* OpenGL jest silnikiem z implementacją nieobiektową, dlatego jego funkcje dostępne są
        w wraperach w obiekcie gl
         */
        //konfiguracja rzutni
        gl?.glViewport(0, 0, width, height)
        //załadowanie jednostkowej macierzy jako macierzy projekcji
        gl?.glMatrixMode(GL10.GL_PROJECTION)
        gl?.glLoadIdentity()
        //konfiguracja perspektywy, korzystamy z GLU czyli narzędzi OpenGL
        GLU.gluPerspective(gl, 45.0f, width.toFloat() / height.toFloat(), -1.0f, -10.0f)
        //ustawienie domyslnego koloru w modelu barw RGBA
        gl?.glClearColor(0.0f, 0.0f, 1.0f, 1.0f)
    }

    //funkcja wywolywana w pętli odpowiedzialna za renederowanie (malowanie) płótna
    override fun onDrawFrame(gl: GL10?) {
        //wierchołki trójkąta w 3D
        val vertices = floatArrayOf(
            -1.0f, 0.0f, -1.0f,
            0.0f, 1.0f, -1.0f,
            1.0f, 0.0f, -1.0f
        )
        //wierchołki trzeba przekazać poza JVM więc trzeba zaalokować bufor
        //3 - wierzchołki, 3 - wymiary 4 - bajty na float
        val buffer: ByteBuffer = ByteBuffer.allocateDirect(3 * 3 * 4)
        //natywny dla sprzętu układ bajtów w słowie maszynowym
        buffer.order(ByteOrder.nativeOrder())
        //do tego bufora chcemy się odwoływać jak do bufora floatów
        val verticesBuffer: FloatBuffer = buffer.asFloatBuffer()
        //wrzucamy wierzchołki do bufora poza JVM
        verticesBuffer.put(vertices)
        //ustawiamy pozycję w buforze
        verticesBuffer.position(0)
        //konfiguracja bufora kolorów i bufora głębi
        gl?.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
        //ustawienie koloru malowania i wypełniania
        gl?.glColor4f(1.0f, 0.0f, 0.0f, 1.0f)
        //załadowanie macierzy jednostkowej do macierzy transformacji
        gl?.glLoadIdentity()
        //obrót figury tj. wymnożenie macierzy transformacji
        gl?.glRotatef(angle++ /*kąt obrotu*/, 0.0f, 0.0f, 1.0f /*oś obrotu*/)
        //przechodzimy do stanu przekazania wierzchołków silnikowi OpenGL
        gl?.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        //przekazujemy do silnika informację o wierzchołkach
        gl?.glVertexPointer(3, GL10.GL_FLOAT, 0, verticesBuffer)
        //zlecamy namalowanie trójkąta
        gl?.glDrawArrays(GL10.GL_TRIANGLES, 0 /*początek*/, 3/*liczba wierz.*/)
        //opuszczamy ten stan
        gl?.glDisableClientState(GL10.GL_VERTEX_ARRAY)
    }
}