import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.diagnostic.trace
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile

class FileSwitchAction : AnAction("File Switch") {
    override fun actionPerformed(e: AnActionEvent) {
        logger<FileSwitchAction>().trace("action")

        val project = e.project
        if (project == null) {
            logger<FileSwitchAction>().trace { "Project not found" }
            return
        }

        val originalFile = e.dataContext.getData("virtualFile") as VirtualFile?
        if (originalFile == null) {
            logger<FileSwitchAction>().trace { "File not found" }
            return
        }

        val newFilePath = "${originalFile.parent.path}/${originalFile.nameWithoutExtension}.html"

        val newFile = LocalFileSystem.getInstance().findFileByPath(newFilePath)
        if (newFile == null) {
            logger<FileSwitchAction>().trace { "New file not found" }
            return
        }

        val fileEditorManager = FileEditorManager.getInstance(project)
        fileEditorManager.closeFile(originalFile)
        fileEditorManager.openFile(newFile, true)
    }
}
