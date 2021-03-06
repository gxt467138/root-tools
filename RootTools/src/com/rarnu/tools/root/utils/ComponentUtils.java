package com.rarnu.tools.root.utils;

import java.io.File;

import android.content.ComponentName;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageParser;

import com.rarnu.tools.root.GlobalInstance;
import com.rarnu.tools.root.common.EnableappInfo;
import com.rarnu.tools.root.utils.root.CommandResult;
import com.rarnu.tools.root.utils.root.RootUtils;

public class ComponentUtils {

	public static boolean doEnableApplication(EnableappInfo info) {
		String source = info.filePath;
		String filePath = "";
		String dest = "";
		if (info.type == 0) {
			dest = "/system/app/";
			filePath = dest + source.replace(DirHelper.ENABLEAPP_DIR_SYSTEM, "");
		} else if (info.type == 1) {
			dest = "/data/app/";
			filePath = dest + source.replace(DirHelper.ENABLEAPP_DIR_DATA, "");
		}
		try {
			CommandResult cmdRet = RootUtils.runCommand(String.format("busybox cp %s %s", source, dest), true);
			if (cmdRet.error.equals("")) {
				cmdRet = RootUtils.runCommand(String.format("rm %s", source), true);
				info.filePath = filePath;
			}
			return cmdRet.error.equals("");
		} catch (Throwable th) {
			return false;
		}
	}

	public static boolean doDisableApplication(EnableappInfo info) {
		String source = info.info.sourceDir;
		String filePath = "";
		String dest = "";
		if (info.type == 0) {
			dest = DirHelper.ENABLEAPP_DIR_SYSTEM;
			filePath = dest + source.replace("/system/app/", "");
		} else if (info.type == 1) {
			dest = DirHelper.ENABLEAPP_DIR_DATA;
			filePath = dest + source.replace("/data/app/", "");
		} else {
			return false;
		}
		try {
			CommandResult cmdRet = RootUtils.runCommand(
					String.format("busybox cp %s %s", source, dest), true);
			if (cmdRet.error.equals("")) {
				cmdRet = RootUtils.runCommand(String.format("rm %s", source),
						true);
				info.filePath = filePath;
			}
			return cmdRet.error.equals("");
		} catch (Throwable th) {
			return false;
		}
	}

	public static boolean doEnabledComponent(ComponentName receiverName) {

		// pm enable
		// com.melodis.midomiMusicIdentifier/com.google.ads.InstallReceiver
		try {
			RootUtils.runCommand(
					String.format("pm enable %s/%s",
							receiverName.getPackageName(),
							receiverName.getClassName()), true);

			return GlobalInstance.pm.getComponentEnabledSetting(receiverName) != PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
		} catch (Throwable th) {
			return false;
		}
	}

	public static boolean doDisableComponent(ComponentName receiverName) {
		// pm disable
		// com.melodis.midomiMusicIdentifier/com.google.ads.InstallReceiver
		try {
			RootUtils.runCommand(
					String.format("pm disable %s/%s",
							receiverName.getPackageName(),
							receiverName.getClassName()), true);
			return GlobalInstance.pm.getComponentEnabledSetting(receiverName) == PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
		} catch (Throwable th) {
			return false;
		}
	}

	public static PackageParser.Package parsePackageInfo(PackageInfo info) {
		String fileAbsPath = info.applicationInfo.publicSourceDir;
		PackageParser packageParser = new PackageParser(fileAbsPath);
		File sourceFile = new File(fileAbsPath);
		PackageParser.Package pkg = packageParser.parsePackage(sourceFile,
				fileAbsPath, GlobalInstance.metric,
				PackageParser.PARSE_IS_SYSTEM);
		return pkg;
	}
}
