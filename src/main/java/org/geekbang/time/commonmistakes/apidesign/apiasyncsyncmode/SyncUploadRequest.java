package org.geekbang.time.commonmistakes.apidesign.apiasyncsyncmode;

import lombok.Data;

@Data
public class SyncUploadRequest {
    private byte[] file;
}
