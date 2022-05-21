import {CakesListResponse, View} from "../types/Types";
import Button from "../component/Button";
import CakesList from "../render/CakesList";
import {useEffect, useState} from "react";
import CakesListService from "../services/CakesListService";

const CakesListView = (
    props: {
        handleChangeView: any,
        handleMessage?: any,
    }
): JSX.Element => {

    const cakesListResponseDefault: CakesListResponse = {
        payload: [],
        errors: []
    };
    const [cakesListResponse, setCakesListResponse] = useState(cakesListResponseDefault);

    const handleCakesList = (cakesListResponse: CakesListResponse): void => {
        if (cakesListResponse.errors.length > 0) {
            props.handleMessage(cakesListResponse.errors[0].message);
        } else {
            setCakesListResponse(cakesListResponse);
        }
    }

    useEffect(() => {
        CakesListService().getCakesList(handleCakesList);
    }, [])

    return (
        <div>
            <div><h1>Cakes</h1></div>
            <Button title="Add cake" onClick={() => {
                props.handleChangeView(View.AddCake)
            }}/>
            <CakesList
                cakesListResponse={cakesListResponse}
            />
        </div>
    )

}

export default CakesListView;