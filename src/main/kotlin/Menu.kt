class Menu {
    private val archives = mutableListOf<Archive>()

    fun start() {
        var isRunning = true
        while (isRunning) {
            showMenu(
                title = "Список архивов:",
                items = listOf("Создать архив") + archives.map { it.name } + "Выход",
                onItemSelected = { index ->
                    when (index) {
                        0 -> createArchive()
                        archives.size + 1 -> isRunning = false
                        else -> openArchive(archives[index - 1])
                    }
                }
            )
        }
    }

    private fun createArchive(){
        val name = readNonEmptyInout("Введите название архива:")
        archives.add(Archive(name))
        println("Архив \"$name\" создан.")
    }

    private fun openArchive(archive: Archive) {
        var isRunning = true
        while (isRunning) {
            showMenu(
                title = "Список заметок в архиве \"${archive.name}\":",
                items = listOf("Создать заметку") + archive.notes.map { it.title } + "Назад",
                onItemSelected = { index ->
                    when (index){
                        0 -> createNote(archive)
                        archive.notes.size + 1 -> isRunning = false
                        else -> openNote(archive.notes[index - 1])
                    }
                }
            )
        }
    }

    private fun showMenu(title: String, items: List<String>, onItemSelected: (Int) -> Unit){
        println("\n$title")
        items.forEachIndexed{ index, item -> println("$index. $item") }
        val input = readlnOrNull()
        val selectedIndex = input?.toIntOrNull()
        if(selectedIndex == null || selectedIndex !in items.indices){
            println("Ошибка: введите число от 0 до ${items.size - 1}.")
        }else{
            onItemSelected(selectedIndex)
        }
    }

    private fun readNonEmptyInout (promt: String): String{
        while (true){
            println(promt)
            val input = readlnOrNull()?.trim()
            if(input.isNullOrEmpty()) println("Ошибка, поле не может быть пустым")
            else return input

        }
    }

    private fun createNote(archive: Archive){
        val title = readNonEmptyInout("Введите название заметки: ")
        val content = readNonEmptyInout("Введите текст заметки: ")
        archive.notes.add(Note(title, content))
        println("Заметка \"$title\" создана.")
    }

    private fun openNote(note: Note){
        println("\nЗаметка: ${note.title}")
        println("Текст: ${note.content}")
        println("\nНажмите Enter, чтобы вернуться")
        readlnOrNull()
    }

}