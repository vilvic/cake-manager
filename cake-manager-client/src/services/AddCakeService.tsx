const AddCakeService = () => {

    async function addCake(handleAddCake: any, title: string, description: string, imageUrl: string) {

        const postRequest = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                title: title,
                description: description,
                imageUrl: imageUrl
            }),
        }
        await fetch('http://localhost:8080/cakes', postRequest)
            .then(value => value.json())
            .then(data => handleAddCake(data))
            .catch(error => {
                handleAddCake({
                        payload: null,
                        errors: [
                            {
                                field: null,
                                message: `Network error - ${error.message}`
                            }
                        ],
                    }
                );
            });
    }

    return {addCake};
}

export default AddCakeService;