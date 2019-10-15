package com.abhishek.topgithub;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

public class MockServerDispatcher {

    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
    private static final int EOF = -1;

    public class RequestDispatcher extends Dispatcher {
        private static final String SUCCESS_RESPONSE_PATH = "/response.json";
        private int testResponseCode = 200;


        public RequestDispatcher() {
        }

        public void setTestResponseCode(int testResponseCode) {
            this.testResponseCode = testResponseCode;
        }

        @Override
        public MockResponse dispatch(RecordedRequest request) {
            try {
                if (testResponseCode == 200 && request.getPath().equals("/developers?language=java&amp;since=weekly")) {
                    String appShortCuts = getResponse(SUCCESS_RESPONSE_PATH);
                    return new MockResponse().setResponseCode(200).setBody(appShortCuts);
                } else if (testResponseCode == 404) {
                    return new MockResponse().setResponseCode(404).setBody("Page Not Found").setHeader("Err", "Page Not Found");
                } else if (testResponseCode == 400) {
                    return new MockResponse().setResponseCode(400).setBody("Bad Request").setHeader("Err", "Bad Request");
                } else if (testResponseCode >= 500) {
                    return new MockResponse().setResponseCode(testResponseCode).setBody("Server Error").setHeader("Err", "Server Error");
                } else {
                    return new MockResponse().setResponseCode(testResponseCode).setBody("Server Down").setHeader("Err", "Server Down");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new MockResponse().setResponseCode(testResponseCode);
        }
    }

    @NonNull
    private String getResponse(String json) throws IOException {
        final InputStream is = getClass().getResourceAsStream(json);
        return toString(is);
    }

    public static String toString(InputStream input) throws IOException {
        return toString(input, Charset.defaultCharset());
    }

    public static String toString(InputStream input, Charset encoding) throws IOException {
        final StringWriter sw = new StringWriter();
        copy(input, sw, encoding);
        return sw.toString();
    }

    private static void copy(InputStream input, Writer output, Charset encoding) throws IOException {
        final InputStreamReader in = new InputStreamReader(input, encoding);
        copy(in, output);
    }

    private static int copy(Reader input, Writer output) throws IOException {
        final long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    private static long copyLarge(Reader input, Writer output) throws IOException {
        return copyLarge(input, output, new char[DEFAULT_BUFFER_SIZE]);
    }

    private static long copyLarge(Reader input, Writer output, char[] buffer) throws IOException {
        long count = 0;
        int n;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
}
