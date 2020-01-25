using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Strum1 : MonoBehaviour
{
    public AudioClip strum1;
    public AudioSource strum1Source;

    // Start is called before the first frame update
    void Start()
    {
        strum1Source.clip = strum1;
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    void OnMouseOver()
    {
        strum1Source.Play();
    }
}
