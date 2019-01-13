package msa.notes

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_note_detail.*
import msa.domain.statemachine.NoteAction
import msa.domain.statemachine.NoteState
import msa.notes.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Created by Abhi Muktheeswarar.
 */

class NoteDetailFragment : BaseFragment() {

    private val notesViewModel by sharedViewModel<NotesViewModel>()

    override fun getLayoutId(): Int = R.layout.fragment_note_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_edit_note.setOnClickListener {

            notesViewModel.input.accept(NoteAction.OpenEditNoteAction)
            findNavController().navigate(R.id.insertUpdateNoteFragment)
        }
    }

    override fun onStart() {
        super.onStart()
        notesViewModel.state.observe(this, Observer {
            setupViews(it as NoteState)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        notesViewModel.input.accept(NoteAction.CloseNoteDetailAction)
    }

    private fun setupViews(state: NoteState) {

        state.note?.let { note ->

            text_title.text = note.title
            text_body.text = note.body

        }
    }
}