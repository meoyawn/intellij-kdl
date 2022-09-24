package dev.kdl.lang.folding

import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.refactoring.suggested.endOffset
import com.intellij.refactoring.suggested.startOffset
import dev.kdl.lang.psi.KdlPsiNodes

class KdlFoldingBuilder: FoldingBuilderEx() {

    override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {

        val descriptors = PsiTreeUtil.findChildrenOfType(root, KdlPsiNodes::class.java)
            .mapNotNull { node ->
                val range = TextRange.create(node.startOffset, node.endOffset)
                if (range.length == 0) {
                    return@mapNotNull null
                }

                FoldingDescriptor(node, range)
            }

        return descriptors.toTypedArray()
    }

    override fun getPlaceholderText(node: ASTNode): String = "..."

    override fun isCollapsedByDefault(node: ASTNode): Boolean = false
}