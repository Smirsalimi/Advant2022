import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.required
import java.nio.file.Paths
import kotlin.io.path.readText
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

fun main(args: Array<String>) {
    val parser = ArgParser("2022")
    val dayArg by parser.option(
        ArgType.Int,
        shortName = "d",
        fullName = "day",
        description = "Day number"
    ).required()
    val inputFileArg by parser.option(
        ArgType.String,
        shortName = "i",
        fullName = "in-file",
        description = "Input file path"
    )
    parser.parse(args)

    val path =  Paths.get(inputFileArg?:defaultInput(dayArg))
    val paddedDayNum = dayArg.toString().padStart(2, '0')
    println("Processing: $path for day $paddedDayNum")

    val starClass: KClass<out Star>? = Star::class.sealedSubclasses.find { it.simpleName!!.endsWith(paddedDayNum) }
    starClass?.let {
        val star: Star = starClass.primaryConstructor!!.call(path.readText())
        println("--> $dayArg.1: ${star.part1()}")
        println("--> $dayArg.2: ${star.part2()}")
    } ?: println("--> No solution (yet)")
}

fun defaultInput(day: Int): String {
    return "data/input$day"
}
