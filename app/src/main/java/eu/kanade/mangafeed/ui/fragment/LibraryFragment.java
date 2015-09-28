package eu.kanade.mangafeed.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import eu.kanade.mangafeed.R;
import eu.kanade.mangafeed.data.models.Manga;
import eu.kanade.mangafeed.presenter.LibraryPresenter;
import eu.kanade.mangafeed.ui.activity.BaseActivity;
import eu.kanade.mangafeed.ui.adapter.MangaLibraryHolder;
import eu.kanade.mangafeed.view.LibraryView;
import uk.co.ribot.easyadapter.EasyAdapter;


public class LibraryFragment extends Fragment implements LibraryView {

    @Bind(R.id.gridView) GridView grid;
    LibraryPresenter mLibraryPresenter;
    EasyAdapter<Manga> mEasyAdapter;

    public static LibraryFragment newInstance() {
        LibraryFragment fragment = new LibraryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLibraryPresenter = new LibraryPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        ((BaseActivity) getActivity()).getSupportActionBar().setTitle(R.string.library_title);
        ButterKnife.bind(this, view);

        mLibraryPresenter.initializeMangas();
        setMangaClickListener();

        return view;
    }

    public void setMangas(ArrayList<Manga> mangas) {
        if (mEasyAdapter == null) {
            mEasyAdapter = new EasyAdapter<Manga>(
                    getActivity(),
                    MangaLibraryHolder.class,
                    mangas
            );
            grid.setAdapter(mEasyAdapter);
        } else {
            mEasyAdapter.setItems(mangas);
        }

    }

    private void setMangaClickListener() {
        grid.setOnItemClickListener(
                (parent, view, position, id) ->
                    mLibraryPresenter.onMangaClick(mEasyAdapter, position)
        );
    }

}
