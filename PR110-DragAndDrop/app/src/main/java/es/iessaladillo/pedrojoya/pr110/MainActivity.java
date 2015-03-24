package es.iessaladillo.pedrojoya.pr110;

import android.content.ClipData;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private TextView mLblLeyenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
    }

    private void initVistas() {
        ImageView mImgOrigen = (ImageView) findViewById(R.id.imgOrigen);
        mLblLeyenda = (TextView) findViewById(R.id.lblTitulo);
        FrameLayout mFrlCuadro = (FrameLayout) findViewById(R.id.frlCuadro);
        FrameLayout mFrlLeyenda = (FrameLayout) findViewById(R.id.frlLeyenda);
        RelativeLayout mRlRaiz = (RelativeLayout) findViewById(R.id.rlRaiz);

        // El proceso de drag and drop se inicará cuando se realice
        // un click largo sobre la vista.
        mImgOrigen.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                v.startDrag(null,  // Datos a arrastrar
                        new View.DragShadowBuilder(v),  // Sombra
                        v,  // Se establece la vista como datos locales.
                        0   // flags (sin uso)
                );
                // Se oculta la vista.
                v.setVisibility(View.INVISIBLE);
                return true;
            }
        });

        // El proceso de drag and drop se inicará cuando se realice
        // un click largo sobre la vista.
        mLblLeyenda.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                v.startDrag(ClipData.newPlainText("Leyenda", mLblLeyenda.getText().toString()),
                        new View.DragShadowBuilder(v),
                        v,
                        0
                );
                // Se oculta la vista.
                v.setVisibility(View.INVISIBLE);
                return true;
            }
        });

        // Se prepara al receptor para recibir los eventos del drag and drop.
        mFrlCuadro.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                // Dependiendo de la acción.
                switch (event.getAction()) {
                    // Se ha iniciado la operación.
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d(getString(R.string.app_name), "frlCuadro: DragStarted");
                        // Si no es una imagen no
                        ImageView imagen;
                        try {
                            imagen = (ImageView) event.getLocalState();
                        } catch (Exception e) {
                            return false;
                        }
                        if (imagen == null) {
                            return false;
                        }
                        break;
                    // La sombra de arrastre ha tocado los límites de la vista.
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d(getString(R.string.app_name), "frlCuadro: DragEntered");
                        // Se cambia el color de fondo del posible receptor.
                        v.setBackgroundColor(getResources().getColor(R.color.cuadroCandidatoBackground));
                        break;
                    // La sombra de arrastre ha salido de los límites de la vista.
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.d(getString(R.string.app_name), "frlCuadro: DragExited");
                        // Se restablece el color de fondo del candidato.
                        v.setBackgroundColor(getResources().getColor(R.color.cuadroBackground));
                        break;
                    // Si se deja caer sobre otra vista.
                    case DragEvent.ACTION_DROP:
                        Log.d(getString(R.string.app_name), "frlCuadro: Drop");
                        // Se obtiene la vista que se ha dejado caer.
                        View original = (View) event.getLocalState();
                        // Se quita de su padre.
                        ((ViewGroup) original.getParent()).removeView(original);
                        // Se agrega al destino de manera que aparezca centrado..
                        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                                FrameLayout.LayoutParams.WRAP_CONTENT,
                                FrameLayout.LayoutParams.WRAP_CONTENT);
                        lp.gravity = Gravity.CENTER;
                        original.setLayoutParams(lp);
                        ((ViewGroup) v).addView(original);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.d(getString(R.string.app_name), "frlCuadro: DragEnded");
                        // Se restablece el color orginal del destinatario.
                        v.setBackgroundColor(getResources().getColor(R.color.cuadroBackground));
                        // Se vuelve a hacer visible la vista (importante que no sea inmediatamente)
                        final View arrastrada = (View) event.getLocalState();
                        if (arrastrada != null) {
                            v.post(new Runnable() {
                                @Override
                                public void run() {
                                    arrastrada.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                        return true;
                }
                return true;
            }
        });

        mFrlLeyenda.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    // Se ha iniciado la operación.
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d(getString(R.string.app_name), "frlLeyenda: DragStarted");
                        // Si la vista arrastrada no es un TextView, no es candidata.
                        TextView leyenda;
                        try {
                            leyenda = (TextView) event.getLocalState();
                        } catch (Exception e) {
                            return false;
                        }
                        if (leyenda == null) {
                            return false;
                        }
                        break;
                    // La sombra de arrastre ha tocado los límites de la vista.
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d(getString(R.string.app_name), "frlLeyenda: DragEntered");
                        v.setBackgroundColor(getResources().getColor(R.color.cuadroCandidatoBackground));
                        break;
                    // La sombra de arrastre ha salido de los límites de la vista.
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.d(getString(R.string.app_name), "frlLeyenda: DragExited");
                        v.setBackgroundColor(getResources().getColor(R.color.leyendaBackground));
                        break;
                    // Si se deja caer sobre otra vista.
                    case DragEvent.ACTION_DROP:
                        Log.d(getString(R.string.app_name), "frlLeyenda: Drop");
                        // Se obtiene la vista que se ha dejado caer.
                        View original = (View) event.getLocalState();
                        // Se quita de su padre.
                        ((ViewGroup) original.getParent()).removeView(original);
                        // Se agrega al destino.
                        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                        lp.gravity = Gravity.CENTER;
                        original.setLayoutParams(lp);
                        ((ViewGroup) v).addView(original);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.d(getString(R.string.app_name), "frlLeyenda: DragEnded");
                        v.setBackgroundColor(getResources().getColor(R.color.leyendaBackground));
                        final View arrastrada = (View) event.getLocalState();
                        if (arrastrada != null) {
                            v.post(new Runnable() {
                                @Override
                                public void run() {
                                    arrastrada.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                        return true;
                }
                return true;
            }
        });

        mRlRaiz.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    // Si se deja caer sobre otra vista.
                    case DragEvent.ACTION_DROP:
                        Log.d(getString(R.string.app_name), "rlRaiz: Drop");
                        // Se obtiene la vista que se ha dejado caer.
                        View original = (View) event.getLocalState();
                        // Se quita de su padre.
                        ((ViewGroup) original.getParent()).removeView(original);
                        // Se agrega al destino.
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(Math.round(event.getX() - original.getWidth() / 2), Math.round(event.getY() - original.getHeight() / 2), 0, 0);
                        original.setLayoutParams(lp);
                        ((ViewGroup) v).addView(original);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.d(getString(R.string.app_name), "frlLeyenda: DragEnded");
                        v.setBackgroundColor(getResources().getColor(R.color.leyendaBackground));
                        final View arrastrada = (View) event.getLocalState();
                        if (arrastrada != null) {
                            v.post(new Runnable() {
                                @Override
                                public void run() {
                                    arrastrada.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                        return true;
                }
                return true;
            }
        });

    }

}