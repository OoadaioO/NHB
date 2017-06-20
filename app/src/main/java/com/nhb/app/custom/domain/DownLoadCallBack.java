package com.nhb.app.custom.domain;

import com.fast.library.utils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.nhb.app.custom.domain.NHBResponse.ERR_IO;


/**
 * <p>***********************************************************************
 * <p> Author: pengxiaofang
 * <p> CreateData: 2016-06-15 17:57
 * <p> Version: 1.0
 * <p> Description: 下载文件回调
 * <p>
 * <p>***********************************************************************
 */

public abstract class DownLoadCallBack implements retrofit2.Callback<ResponseBody> {

	private String fileAbsolutePath;

	/**
	 * 文件下载后保存的绝对路径,eg:/storage/emulated/0/file/icon.png
	 *
	 * @param fileAbsolutePath
	 */
	public DownLoadCallBack(String fileAbsolutePath) {
		this.fileAbsolutePath = fileAbsolutePath;
	}

	@Override
	public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
		onComplete(call);
		if (response.isSuccessful()) {
			boolean writtenToDisk = writeResponseBodyToDisk(response.body());
			if (writtenToDisk) {
				// 保存成功
				onSuccess(fileAbsolutePath);
			} else {
				// 保存失败
				onError(ERR_IO, "文件存储失败,请检查您的SD卡状态");
			}
		} else {
			onError(response.code(), null == response.message() ? "" : response.message());
		}
	}

	@Override
	public void onFailure(Call<ResponseBody> call, Throwable t) {
		onComplete(call);
		onError(NHBResponse.ERR_NETWORK, "网络或服务器开小差,请稍后再试~！");
		t.printStackTrace();
	}

	public abstract void onSuccess(String fileAbsolutePath);

	/**
	 * 包含Http error和业务error
	 *
	 * @param errorCode
	 * @param errorMessage
	 */
	public abstract void onError(int errorCode, String errorMessage);

	public void onComplete(Call call) {

	}

	/**
	 * 将下载的文件保存到本地
	 * @param body
	 * @return
	 */
	private boolean writeResponseBodyToDisk(ResponseBody body) {
		try {
			File futureStudioIconFile = new File(fileAbsolutePath);

			InputStream inputStream = null;
			OutputStream outputStream = null;

			try {
				byte[] fileReader = new byte[1024];

				long fileSize = body.contentLength();
				long fileSizeDownloaded = 0;

				inputStream = body.byteStream();
				outputStream = new FileOutputStream(futureStudioIconFile);

				while (true) {
					int read = inputStream.read(fileReader);

					if (read == -1) {
						break;
					}

					outputStream.write(fileReader, 0, read);

					fileSizeDownloaded += read;

					LogUtils.d("DownLoadCallBack", "file download: " + fileSizeDownloaded + " of " + fileSize);
				}

				outputStream.flush();

				return true;
			} catch (IOException e) {
				return false;
			} finally {
				if (inputStream != null) {
					inputStream.close();
				}

				if (outputStream != null) {
					outputStream.close();
				}
			}
		} catch (IOException e) {
			return false;
		}
	}
}
