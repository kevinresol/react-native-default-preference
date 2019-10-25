declare module 'react-native-default-preference' {
    export default class DefaultPreference {
        static get(key: String): Promise<String>;
        static set(key: String, value: String): Promise<void>;
        static clear(key: String): Promise<void>;
        static getMultiple(keys: Array<String>): Promise<Array<String>>;
        static setMultiple(data: { [key: string]: string }): Promise<void>;
        static clearMultiple(keys: Array<String>): Promise<void>;
        static getAll(): Promise<{ [key: string]: string }>;
        static clearAll(): Promise<void>;

        /** Gets and sets the current preferences file name (android) or user default suite name (ios) **/
        static getName(): Promise<String>;
        static setName(name: String): Promise<void>;
    }
}