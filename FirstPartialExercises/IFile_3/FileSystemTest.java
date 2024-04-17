 package Napredno_Programiranje.FirstPartialExercises.IFile_3;
//zaebanka malce so pecatenjeto poso e prva od vakov tip..

import java.util.*;
import java.util.stream.Collectors;

class FileNameExistsException extends Exception{
    public FileNameExistsException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
interface IFile{
    public String getFileName();
    public long getFileSize();
    public String getFileInfo(int i);
    public void sortBySize();
    public long findLargestFile();
}
class File implements IFile{
    private String name;
    private long size;

    public File(String name, long size) {
        this.name = name;
        this.size = size;
    }
    @Override
    public String getFileName() {//ok
        return name;
    }

    @Override
    public long getFileSize() {//ok
        return size;
    }

    @Override
    public String getFileInfo(int intend) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < intend; i++) {
            sb.append("    ");
        }
        sb.append(String.format("File name: %10s File size: %10d\n",name,size));
        return sb.toString();
    }

    @Override
    public void sortBySize() {//seedno
        //nome interesa
    }

    @Override
    public long findLargestFile() {//seedno
        return size;
    }

}
class Folder extends File{
    List<IFile> files;

    public Folder(String name) {
        super(name,0);
        files = new ArrayList<>();
    }

    public void addFile (IFile file) throws FileNameExistsException {
        if(files.isEmpty()){
            files.add(file);
            return;
        }
        Optional<IFile> opt = files.stream().filter(f->f.getFileName().equals(file.getFileName())).findAny();
        if (opt.isPresent()){
            throw new FileNameExistsException("There is already a file named "+file.getFileName()+" in the folder "+getFileName());
        }
        files.add(file);

    }

    @Override
    public long getFileSize() {//ok
        return files.stream()
                .mapToLong(IFile::getFileSize)
                .sum();
    }

    @Override
    public String getFileInfo(int intend) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < intend; i++) {
            sb.append("    ");
        }
        sb.append(String.format("Folder name: %10s Folder size: %10d\n", getFileName(), getFileSize()));
        files.stream().forEach(f->sb.append(f.getFileInfo(intend+1)));
        return sb.toString();
    }

    @Override
    public void sortBySize() {
        files =  files.stream()
                .sorted(Comparator.comparingLong(IFile::getFileSize))
                .collect(Collectors.toList());
        files.stream()
                .sorted(Comparator.comparingLong(IFile::getFileSize))
                .forEach(IFile::sortBySize);
    }

    @Override
    public long findLargestFile() {
        OptionalLong opt =  files.stream().mapToLong(IFile::findLargestFile).max();
        if (opt.isPresent()){
            return opt.getAsLong();
        }else return 0;
    }

}
class FileSystem{
    private Folder rootDirectory;

    public FileSystem() {
        rootDirectory = new Folder("root");
    }
    public void addFile(IFile file) throws FileNameExistsException {
        rootDirectory.addFile(file);
    }
    public long findLargestFile (){
        return rootDirectory.findLargestFile();
    }
    public void sortBySize(){
        rootDirectory.sortBySize();
    }

    @Override
    public String toString() {
        return rootDirectory.getFileInfo(0);
    }
}
public class FileSystemTest {

    public static Folder readFolder (Scanner sc)  {

        Folder folder = new Folder(sc.nextLine());
        int totalFiles = Integer.parseInt(sc.nextLine());

        for (int i=0;i<totalFiles;i++) {
            String line = sc.nextLine();

            if (line.startsWith("0")) {
                String fileInfo = sc.nextLine();
                String [] parts = fileInfo.split("\\s+");
                try {
                    folder.addFile(new File(parts[0], Long.parseLong(parts[1])));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            }
            else {
                try {
                    folder.addFile(readFolder(sc));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return folder;
    }

    public static void main(String[] args)  {

        //file reading from input

        Scanner sc = new Scanner (System.in);

        System.out.println("===READING FILES FROM INPUT===");
        FileSystem fileSystem = new FileSystem();
        try {
            fileSystem.addFile(readFolder(sc));
        } catch (FileNameExistsException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("===PRINTING FILE SYSTEM INFO===");
        System.out.println(fileSystem.toString());

        System.out.println("===PRINTING FILE SYSTEM INFO AFTER SORTING===");
        fileSystem.sortBySize();
        System.out.println(fileSystem.toString());

        System.out.println("===PRINTING THE SIZE OF THE LARGEST FILE IN THE FILE SYSTEM===");
        System.out.println(fileSystem.findLargestFile());




    }
}