package com.kraftropic.kraftbar.core;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.kraftropic.kraftbar.R;

import java.util.Objects;

public class ToastBar extends BottomSheetDialogFragment implements View.OnClickListener {

    private ProgressBar progressBar;
    private TextView progressSummary, progressImage;

    public ToastBar() {
    }

    public static ToastBar newInstance(String action, String summary, boolean cancellable) {
        ToastBar progressDialog = new ToastBar();
        progressDialog.setCancelable(false);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.action, action);
        bundle.putString(Constants.summary, summary);
        bundle.putBoolean(Constants.cancellable, cancellable);
        progressDialog.setArguments(bundle);
        return progressDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bottom_progress_dialog, container, false);

        progressBar = v.findViewById(R.id.progressBar);
        ImageView progressCancel = v.findViewById(R.id.progressClose);
        progressImage = v.findViewById(R.id.progressImage);
        progressSummary = v.findViewById(R.id.progressSummary);

        assert getArguments() != null;
        if (getArguments().getBoolean(Constants.cancellable)) {
            progressCancel.setVisibility(View.VISIBLE);
            progressCancel.setOnClickListener(this);
        }
        initDialog(Objects.requireNonNull(getArguments().getString(Constants.action)), getArguments().getString(Constants.summary));

        if (getDialog() != null)
            getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    BottomSheetDialog d = (BottomSheetDialog) dialog;
                    FrameLayout bottomSheet = d.findViewById(R.id.design_bottom_sheet);
                    if (bottomSheet == null)
                        return;
                    BottomSheetBehavior.from(bottomSheet);
                    bottomSheet.setBackground(null);
                }
            });

        return v;
    }

    private void initDialog(String action, String summary) {
        progressSummary.setText(summary);
        switch (action) {
            case Constants.loading:
                progressImage.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                break;
            case Constants.success:
                progressImage.setText("i");
                progressImage.setBackgroundResource(R.drawable.bg_circle_green);
                progressImage.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                break;
            case Constants.failed:
                progressImage.setText("!");
                progressImage.setBackgroundResource(R.drawable.bg_circle_red);
                progressImage.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void delayedDismiss(String action, String summary) {
        initDialog(action, summary);
        (new CountDownTimer(2000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                try {
                    dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
