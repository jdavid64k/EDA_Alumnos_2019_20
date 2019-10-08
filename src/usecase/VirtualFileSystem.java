package usecase;



public class VirtualFileSystem {

    //TODO: Ejercicio 4 Caso de uso


    public void loadFileSystem(String path) {
        throw new RuntimeException("Not yet implemented");
    }


    public String getFileSystem() {
        throw new RuntimeException("Not yet implemented");
    }


    public void moveFileById(int idFile, int idTargetFolder) {
        throw new RuntimeException("Not yet implemented");
    }

    public void removeFileById(int idFile) {
        throw new RuntimeException("Not yet implemented");
    }


    public Iterable<String> findBySubstring(int idStartFile, String substring) {
        throw new RuntimeException("Not yet implemented");
    }

    public Iterable<String> findBySize(int idStartFile, long minSize, long maxSize) {
        throw new RuntimeException("Not yet implemented");
    }

    public String getFileVirtualPath(int idFile) {
        throw new RuntimeException("Not yet implemented");
    }

    public String getFilePath(int idFile) {
        throw new RuntimeException("Not yet implemented");
    }

}
