package org.geekbang.time.commonmistakes.apidesign.apiasyncsyncmode;

import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class FileService {

    private ExecutorService threadPool = Executors.newFixedThreadPool(2);
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private ConcurrentHashMap<String, SyncQueryUploadTaskResponse> downloadUrl = new ConcurrentHashMap<>();

    private String uploadFile(byte[] data) {
        try {
            TimeUnit.MILLISECONDS.sleep(500 + ThreadLocalRandom.current().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "http://www.demo.com/download/" + UUID.randomUUID().toString();
    }

    private String uploadThumbnailFile(byte[] data) {
        try {
            TimeUnit.MILLISECONDS.sleep(1500 + ThreadLocalRandom.current().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "http://www.demo.com/download/" + UUID.randomUUID().toString();
    }

    public UploadResponse upload(UploadRequest request) {
        UploadResponse response = new UploadResponse();
        Future<String> uploadFile = threadPool.submit(() -> uploadFile(request.getFile()));
        Future<String> uploadThumbnailFile = threadPool.submit(() -> uploadThumbnailFile(request.getFile()));
        try {
            response.setDownloadUrl(uploadFile.get(1, TimeUnit.SECONDS));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            response.setThumbnailDownloadUrl(uploadThumbnailFile.get(1, TimeUnit.SECONDS));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public SyncUploadResponse syncUpload(SyncUploadRequest request) {
        SyncUploadResponse response = new SyncUploadResponse();
        response.setDownloadUrl(uploadFile(request.getFile()));
        response.setThumbnailDownloadUrl(uploadThumbnailFile(request.getFile()));
        return response;
    }

    public AsyncUploadResponse asyncUpload(AsyncUploadRequest request) {
        AsyncUploadResponse response = new AsyncUploadResponse();
        String taskId = "upload" + atomicInteger.incrementAndGet();
        response.setTaskId(taskId);
        threadPool.execute(() -> {
            String url = uploadFile(request.getFile());
            downloadUrl.computeIfAbsent(taskId, id -> new SyncQueryUploadTaskResponse(id)).setDownloadUrl(url);
        });
        threadPool.execute(() -> {
            String url = uploadThumbnailFile(request.getFile());
            downloadUrl.computeIfAbsent(taskId, id -> new SyncQueryUploadTaskResponse(id)).setThumbnailDownloadUrl(url);
        });
        return response;
    }

    public SyncQueryUploadTaskResponse syncQueryUploadTask(SyncQueryUploadTaskRequest request) {
        SyncQueryUploadTaskResponse response = new SyncQueryUploadTaskResponse(request.getTaskId());
        response.setDownloadUrl(downloadUrl.getOrDefault(request.getTaskId(), response).getDownloadUrl());
        response.setThumbnailDownloadUrl(downloadUrl.getOrDefault(request.getTaskId(), response).getThumbnailDownloadUrl());
        return response;
    }
}
