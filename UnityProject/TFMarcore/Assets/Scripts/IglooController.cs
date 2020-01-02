using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class IglooController : MonoBehaviour {

    public GameObject Pingu;
    public Transform iglooIsland;
    private ParticleSystem confetti = null;

    private void Start() {
        confetti = transform.GetComponentInChildren<ParticleSystem>();
    }

    private void OnTriggerEnter(Collider collision) {

        if(collision.gameObject.name == "Pingu") {
            if (confetti != null) {
                if (confetti.isPlaying) {
                    confetti.Stop();
                    confetti.time = 0;
                }
                confetti.Play();
            }
        }
    }

    public void OnTouchDetected() {
        Pingu.SendMessage("OnMoveTowards", iglooIsland.position);
    }

}
