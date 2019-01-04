package com.dev.autosize.projectcode.mcv;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

import com.dev.autosize.core.base.mvc.BaseMvcActivity;
import com.dev.autosize.projectcode.R;

/**
 * Created by Administrator on 2018-12-30.
 */

public abstract class BasePermissionActivity extends BaseMvcActivity {

    public static final int PERMISSION_CAMERA = 10001;

    public static final int PERMISSION_STORAGE = 10002;

    public static final int PERMISSION_PHONE = 10003;

    public boolean checkPermission(String permission, int requestCode) {
        if (!isGranted(permission) && isMarshmallow()) {
            // 当前权限未授权，并且系统版本为6.0以上，需要申请权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            } else {
                showPermissionDialog(requestCode);
            }
            return false;
        }
        return true;
    }

    protected boolean isGranted(String permission) {
        int granted = ActivityCompat.checkSelfPermission(this, permission);
        return granted == PackageManager.PERMISSION_GRANTED;
    }

    protected boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    private void showPermissionDialog(int requestCode) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("权限提示")
                .setMessage(getMsg(requestCode))
                .setPositiveButton(R.string.common_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri packageURI = Uri.parse("package:" + getPackageName());
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                        startActivity(intent);
                    }
                }).setNegativeButton(R.string.common_negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }

    private String getMsg(int requestCode) {
        String message = "";
        if (requestCode == PERMISSION_CAMERA) {
            //照相机权限
            message = getString(R.string.home_permission_camera);
        } else if (requestCode == PERMISSION_PHONE) {
            //电话权限
            message = getString(R.string.home_permission_phone);
        } else if (requestCode == PERMISSION_STORAGE) {
            //文件操作权限
            message = getString(R.string.home_permission_storage);
        } else {
            message = getString(R.string.home_permission_default);
        }
        return message;
    }


}
