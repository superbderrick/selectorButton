package supebderrick.github.screenrecorder

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import supebderrick.github.screenrecorder.FileConfig.Companion.FOLDER_NAME
import supebderrick.github.screenrecorder.FileConfig.Companion.NAME_PREFIX
import supebderrick.github.screenrecorder.FileConfig.Companion.OUTPUT_EXT
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class Utills {

    companion object {
        fun hasPermissions(context: Context, vararg permissions: String): Boolean = permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

        fun getFilePath(context: Context): String? {
            val directory = Environment.getExternalStorageDirectory().toString() + File.separator + FOLDER_NAME
            if (Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()) {
                Toast.makeText(context, context.getString(R.string.fail_storage), Toast.LENGTH_SHORT).show()
                return null
            }
            val folder = File(directory)
            var success = true
            if (!folder.exists()) {
                success = folder.mkdir()
            }
            val filePath: String
            if (success) {
                val videoName = NAME_PREFIX + DatetimeHelper.getCurSysDate() + OUTPUT_EXT
                filePath = directory + File.separator + videoName
            } else {
                Toast.makeText(context, context.getString(R.string.fail_directory), Toast.LENGTH_SHORT).show()
                return null
            }
            return filePath
        }

         fun showDialogPermission(context: Context) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.permission_denied)
            builder.setMessage(R.string.permission_denied_message)

            builder.setPositiveButton("OK") { _, _ ->
                (context as Activity).finish()
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
}

class DatetimeHelper {
    companion object {
        const val DATE_TIME_FORMAT = "yyyy-MM-dd_HH-mm-ss"

        fun getCurSysDate(): String {
            return SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(Date())
        }
    }
}