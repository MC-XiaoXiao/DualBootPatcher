/*
 * Copyright (C) 2015  Andrew Gunnerson <andrewgunnerson@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.chenxiaolong.dualbootpatcher.switcher;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import com.github.chenxiaolong.dualbootpatcher.R;
import com.github.chenxiaolong.dualbootpatcher.RomUtils.RomInformation;

public class AddToHomeScreenOptionsDialog extends DialogFragment {
    private static final String ARG_ROM_INFO = "rom_info";

    public interface AddToHomeScreenOptionsDialogListener {
        void onConfirmAddToHomeScreenOptions(RomInformation info, boolean reboot);
    }

    public static AddToHomeScreenOptionsDialog newInstanceFromFragment(Fragment parent, RomInformation info) {
        if (parent != null) {
            if (!(parent instanceof AddToHomeScreenOptionsDialogListener)) {
                throw new IllegalStateException(
                        "Parent fragment must implement AddToHomeScreenOptionsDialogListener");
            }
        }

        AddToHomeScreenOptionsDialog frag = new AddToHomeScreenOptionsDialog();
        frag.setTargetFragment(parent, 0);
        Bundle args = new Bundle();
        args.putParcelable(ARG_ROM_INFO, info);
        frag.setArguments(args);
        return frag;
    }

    public static AddToHomeScreenOptionsDialog newInstanceFromActivity(RomInformation info) {
        AddToHomeScreenOptionsDialog frag = new AddToHomeScreenOptionsDialog();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ROM_INFO, info);
        frag.setArguments(args);
        return frag;
    }

    AddToHomeScreenOptionsDialogListener getOwner() {
        Fragment f = getTargetFragment();
        if (f == null) {
            if (!(getActivity() instanceof AddToHomeScreenOptionsDialogListener)) {
                throw new IllegalStateException(
                        "Parent activity must implement AddToHomeScreenOptionsDialogListener");
            }
            return (AddToHomeScreenOptionsDialogListener) getActivity();
        } else {
            return (AddToHomeScreenOptionsDialogListener) getTargetFragment();
        }
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final RomInformation info = getArguments().getParcelable(ARG_ROM_INFO);

        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .content(R.string.auto_reboot_after_switching)
                .negativeText(R.string.no)
                .positiveText(R.string.yes)
                .onPositive(new SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog,
                                        @NonNull DialogAction which) {
                        AddToHomeScreenOptionsDialogListener owner = getOwner();
                        if (owner != null) {
                            owner.onConfirmAddToHomeScreenOptions(info, true);
                        }
                    }
                })
                .onNegative(new SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog,
                                        @NonNull DialogAction which) {
                        AddToHomeScreenOptionsDialogListener owner = getOwner();
                        if (owner != null) {
                            owner.onConfirmAddToHomeScreenOptions(info, false);
                        }
                    }
                })
                .build();

        setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }
}
