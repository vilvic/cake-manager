import React from "react";

const Button = (
    props: {
        title: string
        onClick?: any
    }
): JSX.Element => {
    return <input
        className={"button"}
        type={"button"}
        title={props.title}
        value={props.title}
        onClick={props.onClick}
    />
}

export default Button;