using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PinguController : MonoBehaviour {

    private float speed = 5f;
    private Vector3 targetPosition;
    private bool isMoving = false;
    private BoxCollider collider;

    private void Start() {
        collider = gameObject.GetComponent<BoxCollider>();
    }

    // Update is called once per frame
    void Update() {
        if (isMoving) {
            // Move our position a step closer to the target.
            float step = speed * Time.deltaTime / 10; // calculate distance to move
            transform.position = Vector3.MoveTowards(transform.position, targetPosition, step);

            // Check if the position of the cube and sphere are approximately equal.
            if (Vector3.Distance(transform.position, targetPosition) < 0.00001f) {
                // Stop penguin
                isMoving = false;
                collider.enabled = true;
            }
        }
    }

    public void OnChangeHatMaterial(Material mat) {
        GameObject hat = GameObject.Find("WoolHat");
        Renderer renderer = hat.GetComponent<Renderer>();
        Material[] materials = renderer.materials;
        materials[materials.Length-1] = mat;
        renderer.materials = materials;
    }

    public void OnMoveTowards(Vector3 position) {
        targetPosition = position;
        isMoving = true;
        collider.enabled = false;

        //transform.position = position;
    }
}
