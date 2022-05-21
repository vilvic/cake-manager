const CakesListService = () => {

    async function getCakesList(handleCakesList: any) {
        await fetch('http://localhost:8080/cakes', {mode: 'cors'})
            .then(value => value.json())
            .then(data => handleCakesList(data))
            .catch(error => {
                    handleCakesList({
                        payload: null,
                        errors: [
                            {
                                field: null,
                                message: `Network error - ${error.message}`
                            }
                        ],
                    });
                }
            )
    }

    return {getCakesList};

}

export default CakesListService;