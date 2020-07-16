package com.newsblur.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;

import com.newsblur.R;
import com.newsblur.network.APIManager;
import com.newsblur.util.AppConstants;
import com.newsblur.util.FeedUtils;

public class DeleteFolderFragment extends DialogFragment {

    private static final String FOLDER_NAME = "folder_name";
    private static final String FOLDER_PARENT = "folder_parent";

    public static DeleteFolderFragment newInstance(String folderName, String folderParent) {
        DeleteFolderFragment frag = new DeleteFolderFragment();
        Bundle args = new Bundle();
        args.putString(FOLDER_NAME, folderName);
        args.putString(FOLDER_PARENT, folderParent);
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final String folderName = getArguments().getString(FOLDER_NAME);
        final String folderParent = getArguments().getString(FOLDER_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getResources().getString(R.string.delete_folder_message, folderName));
        builder.setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String inFolder = "";
                if (!TextUtils.isEmpty(folderParent) && !folderParent.equals(AppConstants.ROOT_FOLDER)) {
                    inFolder = folderParent;
                }
                FeedUtils.deleteFolder(folderName, inFolder, getActivity(), new APIManager(getActivity()));
                DeleteFolderFragment.this.dismiss();
            }
        });
        builder.setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DeleteFolderFragment.this.dismiss();
            }
        });
        return builder.create();
    }
}