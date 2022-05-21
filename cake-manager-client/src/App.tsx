import {ReactElement, useState} from 'react';
import {View} from "./types/Types";
import './App.css';
import CakeListView from "./views/CakeListView";
import AddCakeView from "./views/AddCakeView";

const App = (): ReactElement => {

    const [view, setView] = useState(View.CakesList);
    const [message, setMessage] = useState("");

    const handleChangeView = (view: View): void => {
        handleMessage("");
        setView(view);
    }

    const handleMessage = (message: string): void => {
        setMessage(message);
    }

    const selectView = (view: View): JSX.Element => {
        switch (view) {
            case View.AddCake:
                return <AddCakeView
                    handleChangeView={handleChangeView}
                    handleMessage={handleMessage}
                />
            default:
                return <CakeListView
                    handleChangeView={handleChangeView}
                    handleMessage={handleMessage}
                />
        }
    }

    return (
        <div className={"content"}>
            {(message !== "" &&
                <div className="error-message">{message}</div>
            )}
            {selectView(view)}
        </div>
    );

}

export default App;
