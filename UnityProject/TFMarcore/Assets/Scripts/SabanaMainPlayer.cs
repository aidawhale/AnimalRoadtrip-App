using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using static SabanaAnimal;
using static SabanaProp;

public class SabanaMainPlayer : MonoBehaviour {

    public GameObject Head;
    public GameObject Flowers;

    private Vector3 position;
    private SabanaProp currentProp = null;
    private int flowerCount = 0;

    private float speed = 5f;
    private Vector3 targetPosition;
    private bool isMoving = false;

    // Start is called before the first frame update
    void Start() {
        position = transform.position;
    }

    // Update is called once per frame
    void Update() {
        if (isMoving) {
            // Move our position a step closer to the target.
            float step = speed * Time.deltaTime / 10; // calculate distance to move
            transform.position = Vector3.MoveTowards(transform.position, targetPosition, step);
            transform.LookAt(targetPosition);

            // Check if the position of the cube and sphere are approximately equal.
            if (Vector3.Distance(transform.position, targetPosition) < 0.00001f) {
                // Stop penguin
                isMoving = false;
            }
        }
    }

    public void OnTouchDetected() {
        // ...
    }

    public void OnAnimalClick(SabanaAnimal animal) {
        // Walk to that point
        isMoving = true;
        targetPosition = animal.CheetahPlace.transform.position;

        if(currentProp == null) {
            Handheld.Vibrate();
            return;
        }

        if(animal.animalType == Animal.Lion) {
            Flowers.SetActive(false);
            animal.SendMessage("OnReceivePresent", currentProp);
            currentProp = null;
            return;
        }

        if(animal.desiredGift == currentProp.prop) {
            animal.SendMessage("OnReceivePresent", currentProp);
            currentProp = null;
            return;
        }

        // Else: error
        Handheld.Vibrate();
    }

    public void OnFlowerClick(SabanaFlower flower) {

        // Walk to that point
        isMoving = true;
        targetPosition = flower.transform.position;

        flowerCount++;
        if(flowerCount == 3) {
            Flowers.SetActive(true);
            currentProp = Flowers.GetComponent<SabanaProp>();
        }
    }

    public void OnPropClick(SabanaProp prop) {

        // Walk to that point
        isMoving = true;
        targetPosition = prop.CheetahPlace.transform.position;

        if (currentProp != null) {
            // Player is already carrying something
            Handheld.Vibrate();
            return;
        }
        currentProp = prop;
        currentProp.transform.position = Head.transform.position;

        if (currentProp.prop == Prop.Kite) {
            GameObject.FindGameObjectWithTag("Map").SendMessage("BaseVictory");
        }
    }

    public void OnResetPosition() {
        // Init all parameters
        transform.position = position;
        currentProp = null;
        flowerCount = 0;
        Flowers.SetActive(false);
    }

    public void OnUpdatePosition() {
        position = transform.position;
    }

}
