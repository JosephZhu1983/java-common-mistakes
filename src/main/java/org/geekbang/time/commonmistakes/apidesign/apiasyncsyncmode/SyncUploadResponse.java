package org.geekbang.time.commonmistakes.apidesign.apiasyncsyncmode;

import lombok.Data;

@Data
public class SyncUploadResponse {
    private String downloadUrl;
    private String thumbnailDownloadUrl;
}
