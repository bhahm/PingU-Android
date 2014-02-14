package com.pingu.actionsAndObjects;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

import com.parse.ParseFile;
import com.parse.ParseObject;

import android.content.Context;
import android.util.Log;

public class LocalSender implements ReportSender {
	private final String LOG = LocalSender.class.getSimpleName();
	private final Map<ReportField, String> mMapping = new HashMap<ReportField, String>();
	private FileOutputStream crashReport = null;
	private Context ctx;

	public LocalSender(Context ct) {
		ctx = ct;
	}

	public void send(CrashReportData report) throws ReportSenderException {
		final Map<String, String> finalReport = remap(report);
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		Log.i(LOG, "Report send");
		try {
			Set set = finalReport.entrySet();
			Iterator i = set.iterator();
			String tmp;
			while (i.hasNext()) {
				Map.Entry<String, String> me = (Map.Entry) i.next();
				tmp = "[" + me.getKey() + "]=" + me.getValue();
				buf.write(tmp.getBytes());
			}
			ParseFile myFile = new ParseFile("crash.txt", buf.toByteArray());
			myFile.save();
		} catch (FileNotFoundException e) {
			Log.e(LOG, "IO ERROR", e);
		} catch (IOException e) {
			Log.e(LOG, "IO ERROR", e);
		} catch (com.parse.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Map<String, String> remap(Map<ReportField, String> report) {
		ReportField[] fields = ACRA.getConfig().customReportContent();
		final Map<String, String> finalReport = new HashMap<String, String>(
				report.size());
		for (ReportField field : fields) {
			if (mMapping == null || mMapping.get(field) == null) {
				finalReport.put(field.toString(), report.get(field));
			} else {
				finalReport.put(mMapping.get(field), report.get(field));
			}
		}
		return finalReport;
	}
}
