package es.iessaladillo.pedrojoya.pr094;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.common.view.SlidingTabLayout;

public class MainFragment extends Fragment {

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Se obtiene y retorna la vista para el fragmento.
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Se obtienen e inicializan las vistas.
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        if (getView() != null) {
            // Se configura el ViewPager.
            ViewPager vpPaginador = (ViewPager) getView().findViewById(R.id.vpPaginador);
            vpPaginador.setAdapter(new PaginasAdapter(getActivity(),
                    getChildFragmentManager()));
            // Se configura el SlidingTabLayout.
            SlidingTabLayout stTabs = (SlidingTabLayout) getView().findViewById(R.id.stTabs);
            stTabs.setCustomTabView(R.layout.tab_header, R.id.lblTab);
            stTabs.setViewPager(vpPaginador);
            stTabs.setSelectedIndicatorColors(
                    getResources().getColor(R.color.accent), getResources()
                            .getColor(R.color.accent),
                    getResources().getColor(R.color.accent), getResources()
                            .getColor(R.color.accent),
                    getResources().getColor(R.color.accent));
            //stTabs.setDividerColors(getResources().getColor(android.R.color.white));
        }
    }

}