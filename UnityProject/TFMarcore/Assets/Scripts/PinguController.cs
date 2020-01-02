using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PinguController : MonoBehaviour {

    private float speed = 5f;
    private Vector3 targetPosition;
    private bool isMoving = false;

    // Update is called once per frame
    //void Update() {
    //    if (isMoving) {
    //        // Move our position a step closer to the target.
    //        float step = speed * Time.deltaTime; // calculate distance to move
    //        transform.position = Vector3.MoveTowards(transform.position, targetPosition, step);

    //        // Check if the position of the cube and sphere are approximately equal.
    //        if (Vector3.Distance(transform.position, targetPosition) < 0.001f) {
    //            // Stop penguin
    //            isMoving = false;
    //        }
    //    }
    //}

    public void OnMoveTowards(Vector3 position) {
        targetPosition = position;
        isMoving = true;

        transform.position = position;
    }
}
