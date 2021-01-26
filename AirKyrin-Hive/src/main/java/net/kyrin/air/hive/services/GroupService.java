package net.kyrin.air.hive.services;

import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import net.kyrin.air.hive.*;
import net.kyrin.air.hive.database.models.ServerGroup;
import org.zeroturnaround.zip.ZipUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class GroupService extends GroupServiceGrpc.GroupServiceImplBase {

    private final Hive hive;

    public GroupService(Hive hive) {
        this.hive = hive;
    }

    @Override
    public void getAllGroupNames(RequestAllGroups request, StreamObserver<AllGroups> responseObserver) {
        final AllGroups.Builder builder = AllGroups.newBuilder();
        builder.addAllName(hive.getServerGroupManager().getAll().stream().map(ServerGroup::getName).collect(Collectors.toList()));
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void sendGroupFiles(GroupRequest request, StreamObserver<Group> responseObserver) {
        final String name = request.getName();
        final Path path = Paths.get("hive", "template", name);
        final Path tmpPath = Paths.get("hive", "tmp");
        final Path zipFile = Paths.get("hive", "tmp", name + ".zip");
        if (!Files.exists(tmpPath)){
            try {
                Files.createDirectories(tmpPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!Files.exists(zipFile)) {
            try {
                Files.createFile(zipFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ZipUtil.pack(path.toFile(), zipFile.toFile());

        try {
            final byte[] bytes = Files.readAllBytes(zipFile);
            responseObserver.onNext(Group.newBuilder().setContent(File.newBuilder().setBytes(ByteString.copyFrom(bytes)).build()).setName(name).build());
        } catch (IOException e) {
            e.printStackTrace();
        }
        responseObserver.onCompleted();
    }
}
