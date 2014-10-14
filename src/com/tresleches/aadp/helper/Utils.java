package com.tresleches.aadp.helper;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import android.graphics.Bitmap;

public final class Utils {

	//need to check the size and then resize it first
	public static byte[] CompressConvertBitmapTobyteArray(Bitmap bmp, Bitmap.CompressFormat format) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bmp.compress(format, 100, stream);
		
		return (stream.toByteArray());
	}
	
	public static byte[] ConvertBitmapTobyteArray(Bitmap bmp) {
		final int length = bmp.getByteCount();

        ByteBuffer dst= ByteBuffer.allocate(length);
        bmp.copyPixelsToBuffer( dst);

		return (dst.array());
	}

}
