package com.example.gashangman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gashangman.databinding.FragmentKeyboardBinding

class KeyboardFragment : Fragment() {
    private var _binding: FragmentKeyboardBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }
    private lateinit var hangman: Hangman

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hangman = Hangman(
            lives = 6,
            word = "android",
            guessed = BooleanArray(26),
            isSolved = false
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentKeyboardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            a.setOnClickListener() {
                a.apply {
                    isEnabled = false
                }
            }
            b.setOnClickListener() {
                b.apply {
                    isEnabled = false
                }
            }
            c.setOnClickListener() {
                c.apply {
                    isEnabled = false
                }
            }
            d.setOnClickListener() {
                d.apply {
                    isEnabled = false
                }
            }
            e.setOnClickListener() {
                e.apply {
                    isEnabled = false
                }
            }
            f.setOnClickListener() {
                f.apply {
                    isEnabled = false
                }
            }
            g.setOnClickListener() {
                g.apply {
                    isEnabled = false
                }
            }
            h.setOnClickListener() {
                h.apply {
                    isEnabled = false
                }
            }
            i.setOnClickListener() {
                i.apply {
                    isEnabled = false
                }
            }
            j.setOnClickListener() {
                j.apply {
                    isEnabled = false
                }
            }
            k.setOnClickListener() {
                k.apply {
                    isEnabled = false
                }
            }
            l.setOnClickListener() {
                l.apply {
                    isEnabled = false
                }
            }
            m.setOnClickListener() {
                m.apply {
                    isEnabled = false
                }
            }
            n.setOnClickListener() {
                n.apply {
                    isEnabled = false
                }
            }
            o.setOnClickListener() {
                o.apply {
                    isEnabled = false
                }
            }
            p.setOnClickListener() {
                p.apply {
                    isEnabled = false
                }
            }
            q.setOnClickListener() {
                q.apply {
                    isEnabled = false
                }
            }
            r.setOnClickListener() {
                r.apply {
                    isEnabled = false
                }
            }
            s.setOnClickListener() {
                s.apply {
                    isEnabled = false
                }
            }
            t.setOnClickListener() {
                t.apply {
                    isEnabled = false
                }
            }
            u.setOnClickListener() {
                u.apply {
                    isEnabled = false
                }
            }
            v.setOnClickListener() {
                v.apply {
                    isEnabled = false
                }
            }
            w.setOnClickListener() {
                w.apply {
                    isEnabled = false
                }
            }
            x.setOnClickListener() {
                x.apply {
                    isEnabled = false
                }
            }
            y.setOnClickListener() {
                y.apply {
                    isEnabled = false
                }
            }
            z.setOnClickListener() {
                z.apply {
                    isEnabled = false
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}