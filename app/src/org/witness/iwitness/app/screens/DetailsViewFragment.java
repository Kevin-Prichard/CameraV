package org.witness.iwitness.app.screens;


import info.guardianproject.odkparser.FormWrapper.ODKFormListener;

import org.witness.informacam.InformaCam;
import org.witness.informacam.models.media.IMedia;
import org.witness.iwitness.R;
import org.witness.iwitness.app.EditorActivity;
import org.witness.iwitness.app.screens.forms.OverviewFormFragment;
import org.witness.iwitness.utils.Constants.Codes;
import org.witness.iwitness.utils.Constants.EditorActivityListener;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class DetailsViewFragment extends Fragment implements ODKFormListener {
	View rootView;
	ImageView mediaPreview;
	
	FrameLayout formHolder;
	Fragment overviewFormFragment;
		
	
	@Override
	public View onCreateView(LayoutInflater li, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(li, container, savedInstanceState);
		
		Bundle b = getArguments();
		
		int viewId = R.layout.fragment_editor_details_view_p;
		if(b.getInt(Codes.Extras.SET_ORIENTATION) == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			viewId = R.layout.fragment_editor_details_view_l;
		}
		
		rootView = li.inflate(viewId, null);
		
		mediaPreview = (ImageView) rootView.findViewById(R.id.details_media_preview);

		initLayout();
		
		return rootView;
	}
	
	
	private void initLayout() {
		
		String mediaId = this.getArguments().getString("mediaId");
		
		IMedia media = InformaCam.getInstance().mediaManifest.getById(mediaId);
		String strBmpPath = media.bitmapPreview;
		
		Bitmap bmp = media.getBitmap(strBmpPath);
		
		mediaPreview.setImageBitmap(bmp);
		
		overviewFormFragment = Fragment.instantiate(getActivity(), OverviewFormFragment.class.getName());
		
		FragmentTransaction ft = ((EditorActivity) getActivity()).fm.beginTransaction();
		ft.add(R.id.details_form_holder, overviewFormFragment);
		ft.addToBackStack(null);
		ft.commit();
	}

	@Override
	public boolean saveForm() {
		return ((ODKFormListener) overviewFormFragment).saveForm();
	}
}
