import {AddCakeResponse, View} from "../types/Types";
import {ErrorMessage, Field, Form, Formik} from "formik";
import * as Yup from "yup";
import AddCakeService from "../services/AddCakeService";

const AddCakeView = (
    props: {
        handleChangeView: any,
        handleMessage?: any,
    }
): JSX.Element => {

    const addCakeSchema = Yup.object().shape({
        title: Yup.string()
            .max(100, "Maximum length is 100")
            .required("Title is required"),

        description: Yup.string()
            .max(500, "Maximum length is 500")
            .required("Description is required"),

        imageUrl: Yup.string()
            .max(300, "Maximum length is 300")
            .required("Image url is required"),
    });

    const initialValues = {
        title: "",
        description: "",
        imageUrl: "http://",
    }

    const submitForm = (values: any) => {
        AddCakeService().addCake(handleAddCake, values.title, values.description, values.imageUrl);
    };

    const handleAddCake = (addCakeResponse: AddCakeResponse): void => {
        if (addCakeResponse.errors.length > 0) {
            const message = addCakeResponse.errors.map(value => `${value.field} - ${value.message}`).join(", ");
            props.handleMessage(message);
        } else {
            props.handleChangeView(View.CakesList)
        }
    }

    const addCakeForm = () => {
        return (
            <Formik
                initialValues={initialValues}
                validationSchema={addCakeSchema}
                onSubmit={submitForm}
            >
                {(formik) => {
                    const {
                        values,
                        handleChange,
                        errors,
                        touched,
                        handleBlur,
                        isValid,
                        dirty
                    } = formik;
                    return (
                        <div className="container">
                            <Form>
                                <div className="form-row">
                                    <label htmlFor="title">Title</label>
                                    <Field
                                        type="text"
                                        name="title"
                                        id="title"
                                        value={values.title}
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        className={errors.title && touched.title ? "input-error" : undefined}
                                    />
                                    <ErrorMessage
                                        name="title"
                                        component="span"
                                        className="error"
                                    />
                                </div>
                                <div className="form-row">
                                    <label htmlFor="description">Description</label>
                                    <Field
                                        type="text"
                                        name="description"
                                        id="description"
                                        value={values.description}
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        className={errors.description && touched.description ? "input-error" : undefined}
                                    />
                                    <ErrorMessage
                                        name="description"
                                        component="span"
                                        className="error"
                                    />
                                </div>
                                <div className="form-row">
                                    <label htmlFor="imageUrl">Image URL</label>
                                    <Field
                                        type="text"
                                        name="imageUrl"
                                        id="imageUrl"
                                        value={values.imageUrl}
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        className={errors.imageUrl && touched.imageUrl ? "input-error" : undefined}
                                    />
                                    <ErrorMessage
                                        name="imageUrl"
                                        component="span"
                                        className="error"
                                    />
                                </div>

                                <button
                                    type="button"
                                    className="button"
                                    onClick={() => {
                                        props.handleChangeView(View.CakesList)
                                    }}
                                >
                                    Cancel
                                </button>
                                <button
                                    type="submit"
                                    className={!(dirty && isValid) ? "disable-btn" : "button"}
                                    disabled={!(dirty && isValid)}
                                >
                                    Submit
                                </button>
                            </Form>
                        </div>
                    )
                }}
            </Formik>

        );
    }

    return (
        <div>
            <div><h1>Add Cake</h1></div>
            <div>
                {addCakeForm()}
            </div>
        </div>
    )

}

export default AddCakeView;