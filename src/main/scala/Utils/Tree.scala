package com.github.scalamaze.utils

class Tree {
  var parent: Option[Tree] = None

  @annotation.tailrec
  private def go(tree: Tree): Tree = if (tree.parent.isDefined) go(tree.parent.get) else tree

  def root: Tree = go(this)

  def isConnectedTo(tree: Tree): Boolean = this.root == tree.root

  def connectTo(tree: Tree): Unit = tree.root.parent = Some(this)

}
