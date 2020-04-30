package org.geekbang.time.commonmistakes.apidesign.apiasyncsyncmode;

import lombok.Data;

@Data
public class AsyncUploadRequest {
    private byte[] file;
}
