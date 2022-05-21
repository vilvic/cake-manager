import {CakesListResponse} from "../types/Types";

const CakesList = (
    props: {
        cakesListResponse: CakesListResponse
    }
): JSX.Element => {
    return (
        <div className={"div-table"}>
            <div className={"div-table-head"}>
                <div className={"div-table-col"}>Title</div>
                <div className={"div-table-col"}>Description</div>
                <div className={"div-table-col"}>Image</div>
            </div>
            <div className={"div-table-body"}>
                {props.cakesListResponse.payload.map((cake) => (
                    <div className={"div-table-row"} key={cake.id}>
                        <div className={"div-table-col"}>{cake.title}</div>
                        <div className={"div-table-col"}>{cake.description}</div>
                        <div className={"div-table-col"}><img className={"image-size"} alt={cake.title} src={cake.imageUrl}/></div>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default CakesList;