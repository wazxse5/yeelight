package com.wazxse5.yeelight.cli

import scala.collection.mutable

class CliCommand private (val stack: mutable.Stack[String]) {
  def top: String = stack.top
  def topOpt: Option[String] = if (isEmpty) None else Some(top)
  def pop: String = stack.pop()
  def pop2: List[String] = popN(2)
  def pop3: List[String] = popN(3)
  def popN(n: Int): List[String] = {
    if (size <= n) stack.popAll().reverse.toList
    else (1 to n).foldLeft(List.empty[String])((l, _) => l :+ pop)
  }
  def popOpt: Option[String] = if (isEmpty) None else Some(pop)
  def push(elem: String): Unit = stack.push(elem)
  def tail: CliCommand = {
    pop
    this
  }
  def isEmpty: Boolean = stack.isEmpty
  def nonEmpty: Boolean = stack.nonEmpty
  def size: Int = stack.size
  def mkString: String = stack.mkString(" ")
  override def clone: CliCommand = new CliCommand(stack.clone())
}

object CliCommand {
  def apply(wholeCommand: String): CliCommand = {
    apply(wholeCommand.split(' '))
  }

  def apply(words: Seq[String]): CliCommand = {
    new CliCommand(mutable.Stack.from(words))
  }

  implicit class CommandOperations(commandWord: String) {
    def isIncludedIn(keyWords: Seq[String]): Boolean = keyWords.contains(commandWord)
  }
}
