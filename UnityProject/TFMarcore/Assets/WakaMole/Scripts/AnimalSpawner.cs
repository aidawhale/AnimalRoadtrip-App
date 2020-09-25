using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Mirror;

public class AnimalSpawner : NetworkBehaviour
{
    public float spawnTime = 2;
    public float spawnRepeatRate = 5;

    public List<GameObject> HoleList;
    public List<GameObject> AnimalList;

    public void StartWakaMoleGame()
    {
        this.gameObject.SetActive(true);
        InvokeRepeating("SpawnAnimal", this.spawnTime, this.spawnRepeatRate);
    }

    private void OnDisable()
    {
        CancelInvoke();
    }

    private void SpawnAnimal()
    {

        // Pick random animal and place it on random hole
        int animalNumber = UnityEngine.Random.Range(0, AnimalList.Count);
        int holeNumber = UnityEngine.Random.Range(0, HoleList.Count);
        float rotation = UnityEngine.Random.Range(0, 360);

        Vector3 spawnPosition = HoleList[holeNumber].transform.position;
        GameObject animalGO = Instantiate(AnimalList[animalNumber], spawnPosition, Quaternion.identity) as GameObject;
        animalGO.SetActive(true);
        animalGO.transform.Rotate(Vector3.up, rotation);

        GuacamoleAnimalController animal = animalGO.GetComponent<GuacamoleAnimalController>();
        animal.SpawnHoleNumber = holeNumber;

        NetworkServer.Spawn(animalGO, connectionToClient);
    }

}
